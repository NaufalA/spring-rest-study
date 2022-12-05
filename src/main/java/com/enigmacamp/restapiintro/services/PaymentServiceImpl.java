package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.dtos.requests.CoursePaymentResponse;
import com.enigmacamp.restapiintro.models.dtos.responses.CoursePaymentRequest;
import com.enigmacamp.restapiintro.repositories.interfaces.PaymentRepository;
import com.enigmacamp.restapiintro.services.interfaces.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public CoursePaymentResponse pay(CoursePaymentRequest coursePaymentRequest) throws Exception {
        return paymentRepository.findCoursePayment(coursePaymentRequest);
    }
}
