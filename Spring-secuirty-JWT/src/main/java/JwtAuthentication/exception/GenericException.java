package JwtAuthentication.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class GenericException extends RuntimeException{
    public GenericException(Exception exception){
        super(exception.getMessage());
    }
}
