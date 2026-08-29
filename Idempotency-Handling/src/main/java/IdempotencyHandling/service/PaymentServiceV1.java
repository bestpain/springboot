package IdempotencyHandling.service;

import IdempotencyHandling.Repository.PaymentGatewayRepository;
import IdempotencyHandling.Repository.IdempotencyRepository;
import IdempotencyHandling.dto.IdempotencyRecord;
import IdempotencyHandling.dto.PaymentRequest;
import IdempotencyHandling.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

//@Service
@RequiredArgsConstructor
public class PaymentServiceV1 {

    private final IdempotencyRepository idempotencyRepository;

    private final PaymentGatewayRepository paymentGatewayRepository;

    public PaymentResponse processPayment(String idempotencyKey, PaymentRequest paymentRequest) {
        IdempotencyRecord existing = idempotencyRepository.validateAndAdd(idempotencyKey, paymentRequest,
                (amount) -> paymentGatewayRepository.charge(amount));


        if (sameRequest(existing, paymentRequest)) {
            return existing.paymentResponse();
        }

        throw new IllegalArgumentException(
                "Idempotency key already used with a different request");
    }

    // Deprecated
    public PaymentResponse processPaymentV1(String idempotencyKey, PaymentRequest paymentRequest) {
        IdempotencyRecord existing = idempotencyRepository.get(idempotencyKey);
        if (existing == null) {
            System.out.println("Making payment request");
            PaymentResponse result = paymentGatewayRepository.charge(paymentRequest.amount());
            idempotencyRepository.put(idempotencyKey, new IdempotencyRecord(paymentRequest, result));
            return result;
        }
        if (sameRequest(existing, paymentRequest)) {
            return existing.paymentResponse();
        }
        throw new IllegalArgumentException("Idempotency key already used with a different request");
    }

    public static boolean sameRequest(IdempotencyRecord existing, PaymentRequest newRequest) {
        return existing.paymentRequest().orderId().equals(newRequest.orderId())
                && Objects.equals(existing.paymentRequest().amount(), newRequest.amount());
    }
}

