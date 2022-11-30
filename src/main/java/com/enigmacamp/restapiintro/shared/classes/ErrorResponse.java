package com.enigmacamp.restapiintro.shared.classes;

public class ErrorResponse extends CommonResponse {
    public ErrorResponse(Integer code, String message) {
        super.setCode(code);
        super.setStatus("ERROR");
        super.setMessage(message);
    }
}
