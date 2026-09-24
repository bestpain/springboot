package APIDesign.Service;

import APIDesign.DTO.OrderSummaryDto;
import APIDesign.Entity.Order;
import APIDesign.Enums.OrderStatus;
import APIDesign.Repository.OrderRepository;
import APIDesign.Repository.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderSummaryDto> getOrdersForGivenCustomerUsingSpecification(int customerId, OrderStatus orderStatus) {
        Specification<Order> orderSpecification = Specification.allOf(
                OrderSpecification.hasCustomerId(customerId),
                OrderSpecification.hasStatus(orderStatus));


        List<Order> orders = orderRepository.findAll(orderSpecification);
        return orders.stream().map(order -> new OrderSummaryDto(order.getId(), order.getAmount(), order.getStatus())).toList();
    }

    public List<OrderSummaryDto> getOrdersForGivenCustomer(int customerId, OrderStatus orderStatus) {
        List<OrderSummaryDto> orders = orderRepository.getOrdersForCustomerV2(customerId, orderStatus);
        return orders;
    }
}
