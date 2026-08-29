package IdempotencyHandling.controller;

import IdempotencyHandling.dto.PaymentRequest;
import IdempotencyHandling.dto.PaymentResponse;
import IdempotencyHandling.service.PaymentService;
import IdempotencyHandling.service.PaymentServiceV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> payment(@RequestBody @Valid PaymentRequest paymentRequest,
                                                   @RequestHeader("Idempotency-Key") String idempotencyKey){
        return ResponseEntity.ok(paymentService.processPayment(idempotencyKey, paymentRequest));
    }
}
