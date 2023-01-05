package com.enigmacamp.restapiintro.shared.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {
    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
