package AsynchronousProcessing.dto;

public record BookOrderRequest(
        long customerId,
        String product,
        int quantity,
        int amount
) {
}
