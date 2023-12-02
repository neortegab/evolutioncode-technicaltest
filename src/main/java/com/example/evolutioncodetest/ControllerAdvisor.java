package com.example.evolutioncodetest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNotFoundExceptions(NoSuchElementException exception, WebRequest request){
        var body = new LinkedHashMap<String, Object>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", 404);
        body.put("message", exception.getMessage());
        body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<?> handleRestExceptions(RestClientResponseException exception, WebRequest request){
        var body = new LinkedHashMap<String, Object>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", exception.getStatusCode().value());
        body.put("status_message", exception.getStatusText());
        body.put("message", exception.getMessage());
        body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(body, exception.getStatusCode());
    }
}
