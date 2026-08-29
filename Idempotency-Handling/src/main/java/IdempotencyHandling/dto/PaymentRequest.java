package IdempotencyHandling.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotBlank
        String orderId,
        
        @Min(value = 1)
        BigDecimal amount) {
}
