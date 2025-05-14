package com.arka.arka_app.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //* Capturo errores de validación (@Valid y DTOs)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers, 
        HttpStatusCode status, 
        WebRequest request) {
        
        Map<String, String> errors = ex.getBindingResult() //* capturo el error y el msj
                                       .getFieldErrors()
                                       .stream()
                                       .collect(Collectors.toMap(
                                        FieldError::getField,
                                        FieldError::getDefaultMessage
                                        ));
        ErrorResponse body = ErrorResponse.builder()
                                          .timestamp(LocalDateTime.now())
                                          .status(HttpStatus.BAD_REQUEST.value())
                                          .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                          .message("Hay campos inválidos")
                                          .path(request.getDescription(false).replace("uri=", ""))
                                          .validationErrors(errors)
                                          .build();
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //* Cualquier otra excepción no manejada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
        Exception ex,
        WebRequest request
    ){
        ErrorResponse body = ErrorResponse.builder()
                                          .timestamp(LocalDateTime.now())
                                          .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                          .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                          .message(ex.getMessage())
                                          .path(request.getDescription(false).replace("uri=", ""))
                                          .build();
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    

}
