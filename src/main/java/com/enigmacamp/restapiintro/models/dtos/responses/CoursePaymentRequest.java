package com.enigmacamp.restapiintro.models.dtos.responses;

import com.enigmacamp.restapiintro.shared.constants.TransactionType;

public class CoursePaymentRequest {
    String customerId;
    String transactionId;
    TransactionType transactionType;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
