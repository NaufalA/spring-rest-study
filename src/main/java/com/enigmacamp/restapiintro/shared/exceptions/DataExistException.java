package com.enigmacamp.restapiintro.shared.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataExistException extends RuntimeException {
    public DataExistException() {
        super("Data Already Exist");
    }

    public DataExistException(String message) {
        super(message);
    }
}
