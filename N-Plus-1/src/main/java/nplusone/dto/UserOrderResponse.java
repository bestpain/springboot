package nplusone.dto;

import nplusone.entity.Order;
import nplusone.entity.User;
import nplusone.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//public record UserOrderResponse(User user, Order order) {
//}

public record UserOrderResponse(Integer id, String name, Integer orderId, BigDecimal amount, OrderStatus status , LocalDateTime time) {
    @Override
    public String toString() {
        return "UserOrderResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orderId=" + orderId +
                ", amount=" + amount +
                ", status=" + status +
                ", time=" + time +
                '}';
    }
}
