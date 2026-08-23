package nplusone.dto;

import nplusone.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderWithUserResponse(Integer orderId, Integer userId, String name, BigDecimal amount, OrderStatus status,
                                    LocalDateTime time) {
    @Override
    public String toString() {
        return "OrderWithUserResponse{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", amount=" + amount +
                ", status=" + status +
                ", time=" + time +
                '}';
    }
}
