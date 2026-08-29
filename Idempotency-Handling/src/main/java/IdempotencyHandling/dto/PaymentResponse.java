package IdempotencyHandling.dto;

import java.util.UUID;

public record PaymentResponse(UUID paymentId, PaymentStatus paymentStatus) {
}
