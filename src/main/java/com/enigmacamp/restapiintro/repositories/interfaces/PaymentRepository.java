package com.enigmacamp.restapiintro.repositories.interfaces;

import com.enigmacamp.restapiintro.models.dtos.requests.CoursePaymentResponse;
import com.enigmacamp.restapiintro.models.dtos.responses.CoursePaymentRequest;

public interface PaymentRepository {
    CoursePaymentResponse findCoursePayment(CoursePaymentRequest coursePaymentRequest) throws Exception;
}
