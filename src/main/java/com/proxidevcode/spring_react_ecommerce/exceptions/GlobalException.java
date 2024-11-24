package com.proxidevcode.spring_react_ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.proxidevcode.spring_react_ecommerce.dtos.ErrorResponseApi;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseApi> handleException (MethodArgumentNotValidException ex, WebRequest request) {
        ErrorResponseApi errorResponseApi = new ErrorResponseApi();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
    });
    errorResponseApi.setErrors(errors);
    return new ResponseEntity<>(errorResponseApi, HttpStatus.BAD_REQUEST);
}

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponseApi> handleException (ResourceNotFoundException e, WebRequest request) {
        ErrorResponseApi dto = ErrorResponseApi.builder()
        .message(e.getMessage())
        .errorCode(HttpStatus.NOT_FOUND.value())
        .timestamp(LocalDateTime.now())
                .error(request.getDescription(false))
        .build();
        
        
        // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler (BadRequestException.class)
    public ResponseEntity<ErrorResponseApi> handleException (BadRequestException ex, WebRequest request) {
        ErrorResponseApi exceptionResponse = ErrorResponseApi.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .error(request.getDescription(false))
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);

    }
}
