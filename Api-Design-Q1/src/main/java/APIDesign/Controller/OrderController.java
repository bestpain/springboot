package APIDesign.Controller;

import APIDesign.DTO.OrderSummaryDto;
import APIDesign.Enums.OrderStatus;
import APIDesign.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<OrderSummaryDto>> getCustomerOrders(@PathVariable int customerId, @RequestParam(required = false) OrderStatus status){

        return ResponseEntity.ok(orderService.getOrdersForGivenCustomer(customerId, status));
    }
}
