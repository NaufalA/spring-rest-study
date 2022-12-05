package com.enigmacamp.restapiintro.services.interfaces;

import com.enigmacamp.restapiintro.models.dtos.requests.CoursePaymentResponse;
import com.enigmacamp.restapiintro.models.dtos.responses.CoursePaymentRequest;

public interface PaymentService {
    CoursePaymentResponse pay(CoursePaymentRequest coursePaymentRequest) throws Exception;
}
