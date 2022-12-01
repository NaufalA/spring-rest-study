package com.enigmacamp.restapiintro.shared.configs;

import com.enigmacamp.restapiintro.services.ArrayCourseServiceImpl;
import com.enigmacamp.restapiintro.services.CourseServiceImpl;
import com.enigmacamp.restapiintro.services.interfaces.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    CourseService getCourseService(@Value("${storage.type}") String storageType) {
        switch (storageType) {
            case "postgres":
                System.out.println("Using PostgreSQL Database");
                return new CourseServiceImpl();
            case "array":
            default:
                System.out.println("Using Array Database");
                return new ArrayCourseServiceImpl();
        }
    }
}
