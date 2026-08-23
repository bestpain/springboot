package nplusone.controller;

import lombok.RequiredArgsConstructor;
import nplusone.dto.OrderResponse;
import nplusone.dto.OrderWithUserResponse;
import nplusone.dto.UserOrderResponse;
import nplusone.entity.Order;
import nplusone.entity.User;
import nplusone.services.OrderService;
import nplusone.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders/v1")
    public ResponseEntity<List<Order>> getAllOrdersV1(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrdersV1());
    }

    @GetMapping("/orders/v2")
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrdersV2());
    }

    @GetMapping("/orders/user/v1")
    public ResponseEntity<List<OrderWithUserResponse>> getUserDetailsForEachOrderV1(){
        return ResponseEntity.ok(orderService.getUserDetailsForEachOrder());
    }

    @GetMapping("/orders/user/v2")
    public ResponseEntity<List<OrderWithUserResponse>> getUserDetailsForEachOrderV2(){
        return ResponseEntity.ok(orderService.getOrderDetailWithUser());
    }
}