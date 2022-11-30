package com.enigmacamp.restapiintro.shared.classes;

public class ErrorResponse<T> extends CommonResponse {
    private T reason;
    public ErrorResponse(Integer code, String message) {
        super.setCode(code);
        super.setStatus("ERROR");
        super.setMessage(message);
    }

    public ErrorResponse(Integer code, String message, T reason) {
        super.setCode(code);
        super.setStatus("ERROR");
        super.setMessage(message);
        this.reason = reason;
    }

    public T getReason() {
        return reason;
    }

    public void setReason(T reason) {
        this.reason = reason;
    }
}
