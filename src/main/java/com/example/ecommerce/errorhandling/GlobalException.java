package com.example.ecommerce.errorhandling;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
//global exception handling
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public String exceptionhandle(Exception ex){

        return ex.getMessage();
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String > handle(RuntimeException ex){
          return ResponseEntity.
                  status(400).body(ex.getMessage());
    }
}
