package com.example.eschool;
import com.example.eschool.dto.*;
import com.example.eschool.services.MarkService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MarksIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarkService markService;

    @Test
    public void testTeacherAddsMarkForStudent() throws Exception {
        // Перевірка додавання оцінки для учня
        Mockito.doNothing().when(markService).markStudent(anyLong(), anyLong(), anyInt());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/marks/addMark")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":1,\"lessonId\":1,\"mark\":10}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Mark added successfully"));
    }

    @Test
    public void testAddMarkForNonExistentStudent() throws Exception {
        // Перевірка додавання оцінки для неіснуючого учня
        Mockito.doThrow(EntityNotFoundException.class)
                .when(markService)
                .markStudent(999L, 1L, 9);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/marks/addMark")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":999,\"lessonId\":1,\"mark\":9}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testAddMarkForNonExistentLesson() throws Exception {
        // Перевірка додавання оцінки для неіснуючого уроку
        Mockito.doThrow(EntityNotFoundException.class)
                .when(markService)
                .markStudent(1L, 999L, 8);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/marks/addMark")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":1,\"lessonId\":999,\"mark\":8}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testAddInvalidMarkValue() throws Exception {
        // Перевірка додавання недійсної оцінки
        Mockito.doThrow(IllegalArgumentException.class)
                .when(markService)
                .markStudent(1L, 1L, 150);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/marks/addMark")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":1,\"lessonId\":1,\"mark\":150}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetMarkByStudentId() throws Exception {
        // Перевірка отримання всіх оцінок учня
        TeacherDto teacherDto = TestObjectFactory.createSampleTeacher(1L);
        ClassDto _class = TestObjectFactory.createSampleClass(1L);
        StudentDto student = TestObjectFactory.createSampleStudent(2L, "Jane", "Doe", _class);
        LessonPlanDto lp = TestObjectFactory.createSampleLessonPlanDto(1L);
        LessonDto lesson = TestObjectFactory.createSampleLesson(1L, teacherDto, _class, lp);
        MarkDto markDto = TestObjectFactory.createSampleMark(1L, 9, lesson, student);

        List<MarkDto> markList = singletonList(markDto);

        Mockito.when(markService.getMarksByStudentId(1L)).thenReturn(markList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/marks/student/2")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
