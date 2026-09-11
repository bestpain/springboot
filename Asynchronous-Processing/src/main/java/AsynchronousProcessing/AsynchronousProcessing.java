package AsynchronousProcessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsynchronousProcessing {
    public static void main(String[] args) {
        SpringApplication.run(AsynchronousProcessing.class, args);
    }
}
