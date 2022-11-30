package com.enigmacamp.restapiintro.shared.classes;

public class SuccessResponse<T> extends CommonResponse {
    private T data;

    public SuccessResponse(Integer code, String message, T data) {
        super.setCode(code);
        super.setStatus("SUCCESS");
        super.setMessage(message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
