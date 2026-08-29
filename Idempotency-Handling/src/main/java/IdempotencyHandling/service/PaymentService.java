package IdempotencyHandling.service;

import IdempotencyHandling.Repository.IdempotencyRepository;
import IdempotencyHandling.Repository.PaymentGatewayRepository;
import IdempotencyHandling.dto.IdempotencyRecord;
import IdempotencyHandling.dto.PaymentRequest;
import IdempotencyHandling.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final IdempotencyRepository idempotencyRepository;

    private final PaymentGatewayRepository paymentGatewayRepository;

    public PaymentResponse processPayment(String idempotencyKey, PaymentRequest request) {

        // First check: existing completed request
        IdempotencyRecord existing =
                idempotencyRepository.find(idempotencyKey);

        if (existing != null) {
            validateRequest(existing.paymentRequest(), request);
            return existing.paymentResponse();
        }

        // payment lambda
        Function<String, IdempotencyRecord> paymentOperation =
                key -> {

                    System.out.println("Making payment for " + key);

                    PaymentResponse response =
                            paymentGatewayRepository
                                    .charge(request.amount());

                    return new IdempotencyRecord(
                            request,
                            response
                    );
                };

        // Atomic check + payment + store
        IdempotencyRecord record =
                idempotencyRepository.computeIfAbsent(
                        idempotencyKey,
                        paymentOperation
                );

        // The record returned here is the one stored in the map.
        validateRequest(record.paymentRequest(), request);

        return record.paymentResponse();
    }

    private void validateRequest(
            PaymentRequest existing,
            PaymentRequest incoming) {

        if (!sameRequest(existing, incoming)) {
            throw new IllegalArgumentException(
                    "Idempotency key already used with a different request"
            );
        }
    }

    private boolean sameRequest(
            PaymentRequest existing,
            PaymentRequest incoming) {

        return Objects.equals(
                existing.orderId(),
                incoming.orderId()
        )
                && Objects.equals(
                existing.amount(),
                incoming.amount()
        );
    }
}

