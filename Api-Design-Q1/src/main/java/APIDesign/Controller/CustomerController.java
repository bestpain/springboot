package APIDesign.Controller;

import APIDesign.DTO.OrderSummaryDto;
import APIDesign.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<OrderSummaryDto> getCustomerOrders(@PathVariable int customerId){
        customerService.getCustomerDetail(customerId);
        return null;
    }
}
