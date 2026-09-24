package APIDesign.Entity;

import APIDesign.Enums.OrderStatus;
import APIDesign.Service.CustomerService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "orders_ex")
public class Order {

    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + id +
                ", amount=" + amount +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
