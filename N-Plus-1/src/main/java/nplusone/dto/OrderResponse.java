package nplusone.dto;

import nplusone.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(Integer id, BigDecimal amount, OrderStatus status , LocalDateTime time) {
};
