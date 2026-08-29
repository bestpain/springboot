package IdempotencyHandling.dto;

public record IdempotencyRecord(PaymentRequest paymentRequest, PaymentResponse paymentResponse) {
}
