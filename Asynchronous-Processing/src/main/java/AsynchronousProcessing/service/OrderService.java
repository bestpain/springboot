package AsynchronousProcessing.service;

import AsynchronousProcessing.dto.BookOrderRequest;
import AsynchronousProcessing.dto.BookOrderResponse;
import AsynchronousProcessing.dto.Status;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public BookOrderResponse createOrder(BookOrderRequest bookOrderRequest){
        return new BookOrderResponse(10001, Status.CREATED);
    }
}
