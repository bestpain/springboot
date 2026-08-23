package nplusone.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nplusone.dto.OrderResponse;
import nplusone.dto.OrderWithUserResponse;
import nplusone.entity.Order;
import nplusone.entity.User;
import nplusone.repository.OrdersRepository;
import nplusone.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;

    // this will cause infinite recursion since jackson will try to access the user field on each order.
    public List<Order> getAllOrdersV1(){
        return ordersRepository.findAll();
    }

    // we are fixing it by transforming the order to order_response and omit the user field.
    public List<OrderResponse> getAllOrdersV2(){
        List<Order> orders = ordersRepository.findAll();
        return orders.stream().map(order -> new OrderResponse(order.getId(), order.getAmount(),
                order.getStatus(), order.getCreatedAt())).toList();
    }

    // get user details for each order using joins
    public List<OrderWithUserResponse> getUserDetailsForEachOrder(){
        List<OrderWithUserResponse> orderWithUserResponses = ordersRepository.getUserDetailsForEachOrder();
        System.out.println(orderWithUserResponses);
        return  orderWithUserResponses;
    }

    // get user details for each order using join fetch
    @Transactional
    public List<OrderWithUserResponse> getOrderDetailWithUser(){
        List<Order> orders = ordersRepository.getOrderAlongWithUser();
        orders.forEach(order -> order.setAmount(order.getAmount().add(new BigDecimal("5000.55"))));
        return orders.stream().map(order -> new OrderWithUserResponse(order.getId(), order.getUser().getId(), order.getUser().getName(),
                order.getAmount(), order.getStatus(), order.getCreatedAt())).toList();
    }
}