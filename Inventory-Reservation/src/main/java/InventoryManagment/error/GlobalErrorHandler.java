package InventoryManagment.error;

import InventoryManagment.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(GenericError.class)
    public ResponseEntity<Response> GenericErrorHandler(GenericError err){
        return ResponseEntity.ok(new Response(HttpStatus.FORBIDDEN , err.getMessage()));
    }
}
