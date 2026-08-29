package IdempotencyHandling.Repository;

import IdempotencyHandling.dto.IdempotencyRecord;
import IdempotencyHandling.dto.PaymentRequest;
import IdempotencyHandling.dto.PaymentResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Component
public class IdempotencyRepository {
    private final Map<String, IdempotencyRecord> records = new ConcurrentHashMap<>();

    public IdempotencyRecord find(String key) {
        return records.get(key);
    }

    public IdempotencyRecord computeIfAbsent(
            String key,
            Function<String, IdempotencyRecord> operation) {

        return records.computeIfAbsent(key, operation);
    }

    // DEPRECATED
    public IdempotencyRecord validateAndAdd(String key, PaymentRequest paymentRequest, Function<BigDecimal, PaymentResponse> operation) {
        System.out.println(records);
        return records.computeIfAbsent(key, (k) ->
                new IdempotencyRecord(paymentRequest, operation.apply(paymentRequest.amount())
                ));
    }

    // DEPRECATED
    public IdempotencyRecord get(String key) {
        return records.get(key);
    }

    // DEPRECATED
    public IdempotencyRecord put(String key, IdempotencyRecord idempotencyRecord) {
        return records.put(key, idempotencyRecord);
    }
}
