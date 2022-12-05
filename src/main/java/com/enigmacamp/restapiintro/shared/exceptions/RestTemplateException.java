package com.enigmacamp.restapiintro.shared.exceptions;

import org.springframework.http.HttpStatus;

public class RestTemplateException extends RuntimeException {
    private final String serviceName;
    private final HttpStatus status;
    private final String error;

    public RestTemplateException(
            String serviceName,
            HttpStatus status,
            String error
    ) {
        super("Exception at " + serviceName + " (" + status.value() + "): " + error);
        this.serviceName = serviceName;
        this.status = status;
        this.error = error;
    }

    public String getServiceName() {
        return serviceName;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
