package AsynchronousProcessing.service;

import AsynchronousProcessing.dto.BookOrderResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class AnalyticsService {

    public void updateAnalytics(BookOrderResponse response){
        log("ANALYTICS START - order=" + response.orderId());
        try {
            Thread.sleep(2000);

            log("ANALYTICS COMPLETED - order=" + response.orderId());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void updateAnalyticsAsync(BookOrderResponse response){
        log("ANALYTICS START - order=" + response.orderId());
        try {
            Thread.sleep(2000);

            log("ANALYTICS COMPLETED - order=" + response.orderId());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void log(String message) {
        System.out.printf(
                "[%s] [%s] %s%n",
                LocalTime.now(),
                Thread.currentThread().getName(),
                message
        );
    }
}
