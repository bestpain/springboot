package APIDesign.DTO;

import APIDesign.Enums.OrderStatus;

import java.math.BigDecimal;

public record OrderSummaryDto(int orderId, BigDecimal amount, OrderStatus status) {
}
