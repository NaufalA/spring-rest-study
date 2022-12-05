package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.dtos.requests.CoursePaymentResponse;
import com.enigmacamp.restapiintro.models.dtos.responses.CoursePaymentRequest;
import com.enigmacamp.restapiintro.services.interfaces.PaymentService;
import com.enigmacamp.restapiintro.shared.classes.CommonResponse;
import com.enigmacamp.restapiintro.shared.classes.ErrorResponse;
import com.enigmacamp.restapiintro.shared.classes.SuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> pay(@RequestBody CoursePaymentRequest coursePaymentRequest) throws Exception {
        CoursePaymentResponse paymentResponse = paymentService.pay(coursePaymentRequest);

        if (paymentResponse.getStatus()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SuccessResponse<>(
                            "OO",
                            "Payment Success",
                            paymentResponse.getTransactionId()
                    ));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ErrorResponse<>(
                            "X01",
                            "Payment Failed",
                            paymentResponse.getTransactionId()
                    ));
        }
    }
}
