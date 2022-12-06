package com.enigmacamp.restapiintro.repositories;

import com.enigmacamp.restapiintro.models.dtos.requests.CoursePaymentResponse;
import com.enigmacamp.restapiintro.models.dtos.responses.CoursePaymentRequest;
import com.enigmacamp.restapiintro.repositories.interfaces.PaymentRepository;
import com.enigmacamp.restapiintro.shared.classes.ErrorResponse;
import com.enigmacamp.restapiintro.shared.classes.SuccessResponse;
import com.enigmacamp.restapiintro.shared.exceptions.RestTemplateException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${service.payment.url}")
    private String url;

    public PaymentRepositoryImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public CoursePaymentResponse findCoursePayment(CoursePaymentRequest coursePaymentRequest) throws Exception {
        try {
            ResponseEntity<SuccessResponse> response = restTemplate.postForEntity(url, coursePaymentRequest, SuccessResponse.class);

            SuccessResponse<String> responseBody = response.getBody();
            CoursePaymentResponse coursePaymentResponse = new CoursePaymentResponse();

            if (!responseBody.getCode().equals("OK")) {
                coursePaymentResponse.setStatus(true);
                coursePaymentResponse.setTransactionId(responseBody.getData());
            } else {
                coursePaymentResponse.setStatus(false);
                coursePaymentResponse.setTransactionId("");
            }

            return coursePaymentResponse;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                ErrorResponse<String> errorResponse = objectMapper.readValue(
                        Objects.requireNonNull(e.getMessage()).substring(7, e.getMessage().length() - 1),
                        new TypeReference<>() {}
                );
                throw new RestTemplateException(url, HttpStatus.BAD_REQUEST, errorResponse.getReason());
            }
            throw e;
        }
    }
}
