package com.enigmacamp.restapiintro.models.dtos.requests;

public class CoursePaymentResponse {
    private String transactionId;
    private Boolean status;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
