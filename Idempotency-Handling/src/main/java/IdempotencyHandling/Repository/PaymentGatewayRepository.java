package IdempotencyHandling.Repository;

import IdempotencyHandling.dto.PaymentRequest;
import IdempotencyHandling.dto.PaymentResponse;
import IdempotencyHandling.dto.PaymentStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class PaymentGatewayRepository {

    public PaymentResponse charge(BigDecimal amount){
        try {
            System.out.println("Making payment of :: " + amount);
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new PaymentResponse(UUID.randomUUID(), PaymentStatus.SUCCESS);
    }
}
