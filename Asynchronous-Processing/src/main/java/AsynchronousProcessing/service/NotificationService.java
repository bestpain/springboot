package AsynchronousProcessing.service;

import AsynchronousProcessing.dto.BookOrderResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class NotificationService {

    public void sendEmail(BookOrderResponse response) {

        log("EMAIL START - order=" + response.orderId());

        try {
            Thread.sleep(2000);

            log("EMAIL COMPLETED - order=" + response.orderId());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendEmailAsync(BookOrderResponse response) {

        log("EMAIL START - order=" + response.orderId());

        try {
            Thread.sleep(2000);
            throw new RuntimeException();
//            log("EMAIL COMPLETED - order=" + response.orderId());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    // for self invocation testing and breaking async behaviour
    public void handleEmail(BookOrderResponse response){
         sendEmailAsync(response);
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