package AsynchronousProcessing.controller;

import AsynchronousProcessing.config.AsyncConfig;
import AsynchronousProcessing.dto.BookOrderRequest;
import AsynchronousProcessing.dto.BookOrderResponse;
import AsynchronousProcessing.service.AnalyticsService;
import AsynchronousProcessing.service.InvoiceService;
import AsynchronousProcessing.service.NotificationService;
import AsynchronousProcessing.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AnalyticsService analyticsService;
    private final InvoiceService invoiceService;
    private final NotificationService notificationService;
    private final ExecutorService executorService;

    @PostMapping("/order/v1")
    public ResponseEntity<BookOrderResponse> bookOrderV1(@RequestBody BookOrderRequest bookOrderRequest) {
        log("REQUEST START");

        BookOrderResponse response = orderService.createOrder(bookOrderRequest);

        log("Order created: " + response.orderId());

        notificationService.sendEmail(response);

        log("Email method returned");

        invoiceService.generateInvoice(response);

        log("Invoice method returned");

        analyticsService.updateAnalytics(response);

        log("Analytics method returned");

        log("RETURNING HTTP RESPONSE");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/order/v2")
    public ResponseEntity<BookOrderResponse> bookOrder(@RequestBody BookOrderRequest bookOrderRequest) {
        log("REQUEST START");

        BookOrderResponse response = orderService.createOrder(bookOrderRequest);

        log("Order created: " + response.orderId());

        executorService.execute(() -> notificationService.sendEmail(response));

        log("Email method returned");

        executorService.execute(() -> invoiceService.generateInvoice(response));

        log("Invoice method returned");

        executorService.execute(() -> analyticsService.updateAnalytics(response));

        log("Analytics method returned");

        log("RETURNING HTTP RESPONSE");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/order")
    public ResponseEntity<BookOrderResponse> bookOrderAsync(@RequestBody BookOrderRequest bookOrderRequest) {
        log("REQUEST START");

        BookOrderResponse response = orderService.createOrder(bookOrderRequest);

        log("Order created: " + response.orderId());

        notificationService.sendEmailAsync(response);

        log("Email method returned");

        invoiceService.generateInvoiceAsync(response);

        log("Invoice method returned");

        analyticsService.updateAnalyticsAsync(response);

        log("Analytics method returned");

        log("RETURNING HTTP RESPONSE");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    private void log(String message) {
        System.out.printf(
                "[%s] [%s] %s%n",
                java.time.LocalTime.now(),
                Thread.currentThread().getName(),
                message
        );
    }
}
