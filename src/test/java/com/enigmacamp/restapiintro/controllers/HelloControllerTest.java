package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.CourseType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class HelloControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void itShouldSuccess_WhenGetHelloRequestWithQueryParam() {
        String nameParam = "World";

        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                            .get("/hello")
                            .param("name", nameParam)
                    )
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Hello World!"))
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void itShouldFail_WhenGetHelloRequestWithoutQueryParam() {
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                            .get("/hello")
                    )
                    .andExpect(status().isBadRequest())
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
