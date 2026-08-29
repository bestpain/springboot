package IdempotencyHandling;

import IdempotencyHandling.dto.PaymentRequest;
import IdempotencyHandling.dto.PaymentResponse;
import IdempotencyHandling.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.*;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    void shouldReproduceRaceCondition() throws Exception {

        String idempotencyKey = "ABC123";

        PaymentRequest request =
                new PaymentRequest(
                        "ORD-1001",
                        new BigDecimal("5000")
                );

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Both threads must reach this point before we start
        CountDownLatch ready = new CountDownLatch(2);

        // Main thread uses this to release both workers
        CountDownLatch start = new CountDownLatch(1);

        Callable<PaymentResponse> task = () -> {

            // Tell main thread: "I'm ready"
            ready.countDown();

            // Wait until both threads are ready
            start.await();

            return paymentService.processPayment(
                    idempotencyKey,
                    request
            );
        };

        Future<PaymentResponse> future1 = executor.submit(task);
        Future<PaymentResponse> future2 = executor.submit(task);

        // Wait until BOTH worker threads are ready
        ready.await();

        System.out.println("Both threads ready. Starting...");
        try {
            // Release both threads
            start.countDown();

            PaymentResponse response1 = future1.get();
            PaymentResponse response2 = future2.get();

            System.out.println("Response 1: " + response1);
            System.out.println("Response 2: " + response2);
        } finally {
            executor.shutdown();
        }


    }
}