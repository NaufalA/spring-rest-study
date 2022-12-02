package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.CourseType;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseInfoRequestDto;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.services.interfaces.CourseTypeService;
import com.enigmacamp.restapiintro.shared.classes.SuccessResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@SqlGroup(
        @Sql(scripts = "classpath:courses.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
)
@AutoConfigureMockMvc
public class CourseControllerTest {
    String baseUri = "/courses";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CourseTypeService courseTypeService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void itShouldSuccess_WhenCreateCourseWithValidData() {
        List<CourseType> courseType = courseTypeService.getAll(Pageable.unpaged()).getContent();
        courseType.forEach(System.out::println);

        CreateCourseInfoRequestDto courseInfoRequest = new CreateCourseInfoRequestDto();
        courseInfoRequest.setDuration(30);
        courseInfoRequest.setLevel(2);

        CreateCourseRequestDto courseRequest = new CreateCourseRequestDto();
        courseRequest.setTitle("Course 1");
        courseRequest.setDescription("Course 1 Description");
        courseRequest.setSlug("course-1");
        courseRequest.setCourseType(courseType.get(0));
        courseRequest.setCourseInfo(courseInfoRequest);

        try {
            MvcResult createRes = mockMvc.perform(MockMvcRequestBuilders
                            .post(baseUri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(courseRequest))
                    )
                    .andExpect(status().isCreated())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            String createContent = createRes.getResponse().getContentAsString();

            SuccessResponse<Course> createResp = objectMapper.readValue(createContent, new TypeReference<>() {
            });
            System.out.println(createResp.getData());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void itShouldSuccess_WhenGetByValidId() {
        String courseId = "C10";

        try {
            MvcResult getRes = mockMvc.perform(MockMvcRequestBuilders
                            .get(baseUri + "/{id}", courseId)
                    )
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            String getContent = getRes.getResponse().getContentAsString();
            SuccessResponse<Course> getResp = objectMapper.readValue(getContent, new TypeReference<SuccessResponse<Course>>() {
            });
            System.out.println(getResp.getData());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void itShouldSuccess_WhenUpdateCourseWithValidData() {
        Course course = new Course();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        static ObjectMapper getObjectMapper() {
            return new ObjectMapper();
        }
    }
}
