package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.CourseInfo;
import com.enigmacamp.restapiintro.models.CourseType;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseInfoRequestDto;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.services.interfaces.CourseTypeService;
import com.enigmacamp.restapiintro.shared.classes.PagedResponse;
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
    void itShouldSuccess_WhenGetAllWithoutCriteria() {
        try {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders
                            .get(baseUri)
                    )
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            String createContent = res.getResponse().getContentAsString();

            SuccessResponse<PagedResponse<Course>> resp = objectMapper.readValue(createContent, new TypeReference<>() {
            });
            assert resp.getData().getFetchedSize().equals(2);
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
        course.setId("C10");
        course.setTitle("Updated Title");
        course.setDescription("Updated Description");
        course.setSlug("updated-slug");
        CourseType updatedCourseType = new CourseType();
        updatedCourseType.setId("2");
        updatedCourseType.setTypeName("dummy course type 2");
        course.setCourseType(updatedCourseType);
        CourseInfo updatedInfo = new CourseInfo();
        updatedInfo.setId("CI10");
        updatedInfo.setDuration(15);
        updatedInfo.setLevel(2);
        course.setCourseInfo(updatedInfo);

        try {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders
                            .put(baseUri + "/{id}", course.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(course))
                    )
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            String getContent = res.getResponse().getContentAsString();
            SuccessResponse<Course> resp = objectMapper.readValue(getContent, new TypeReference<SuccessResponse<Course>>() {
            });
            Course savedCourse = resp.getData();
            assert savedCourse.getTitle().equals(course.getTitle());
            assert savedCourse.getDescription().equals(course.getDescription());
            assert savedCourse.getSlug().equals(course.getSlug());
            assert savedCourse.getCourseType().getId().equals(course.getCourseType().getId());
            assert savedCourse.getCourseType().getTypeName().equals(course.getCourseType().getTypeName());
            assert savedCourse.getCourseInfo().getDuration().equals(course.getCourseInfo().getDuration());
            assert savedCourse.getCourseInfo().getLevel().equals(course.getCourseInfo().getLevel());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void itShouldSuccess_WhenDeleteCourseWithValidId() {
        String courseId = "C10";

        try {
            MvcResult getRes = mockMvc.perform(MockMvcRequestBuilders
                            .delete(baseUri + "/{id}", courseId)
                    )
                    .andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            String getContent = getRes.getResponse().getContentAsString();
            SuccessResponse<String> getResp = objectMapper.readValue(getContent, new TypeReference<SuccessResponse<String>>() {
            });
            assert getResp.getData().equals(courseId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        static ObjectMapper getObjectMapper() {
            return new ObjectMapper();
        }
    }
}
