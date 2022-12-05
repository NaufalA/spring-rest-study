package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.shared.classes.ErrorResponse;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import com.enigmacamp.restapiintro.shared.exceptions.RestTemplateException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleErrorException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse<>(
                HttpStatus.NOT_FOUND.toString(), "Data Not Found", e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleAnyException(Exception e) {
//        ErrorResponse response = new ErrorResponse<>(
//                HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage()
//        );
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<String>>> handleMethodArgument(MethodArgumentNotValidException e) {
        List<String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorResponse<List<String>> response = new ErrorResponse<>(
                HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST.getReasonPhrase(), fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RestTemplateException.class)
    public ResponseEntity<ErrorResponse<String>> handleRestTemplateException(RestTemplateException e) {
        ErrorResponse<String> response = new ErrorResponse<>(
                "X07", "Rest Template Error", e.getMessage()
        );
        return ResponseEntity.status(e.getStatus()).body(response);
    }
}
