package com.example.eschool;

import com.example.eschool.dto.*;
import com.example.eschool.services.AttendanceService;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AttendanceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceService attendanceService;

    @Test
    public void testMarkAttendanceByStudent() throws Exception {
        // Перевірка успішного відмічання відвідуваності для учня
        Mockito.doNothing().when(attendanceService).markAttendanceByStudent(1L, 1L, true);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/attendance/markByStudent")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":1,\"lessonId\":1,\"status\":true}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Attendance marked successfully"));
    }

    @Test
    public void testMarkAttendanceForNonExistentStudent() throws Exception {
        // Перевірка, як відбувається відмітка відвідуваності для неіснуючого учня
        doThrow(EntityNotFoundException.class)
                .when(attendanceService)
                .markAttendanceByStudent(anyLong(), anyLong(), anyBoolean());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/attendance/markByStudent")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":999,\"lessonId\":1,\"status\":true}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testMarkAttendanceWithInvalidLessonId() throws Exception {
        // Перевірка, як відбувається відмітка відвідуваності з недійсним ID уроку
        doThrow(EntityNotFoundException.class)
                .when(attendanceService)
                .markAttendanceByStudent(anyLong(), eq(999L), anyBoolean());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/attendance/markByStudent")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":1,\"lessonId\":999,\"status\":true}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testMarkAttendanceWithInvalidStatus() throws Exception {
        // Перевірка, як відбувається відмітка відвідуваності з недійсним статусом
        doThrow(IllegalArgumentException.class)
                .when(attendanceService)
                .markAttendanceByStudent(anyLong(), anyLong(), anyBoolean());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/attendance/markByStudent")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\":1,\"lessonId\":1,\"status\":null}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Status must be either true or false"));
    }

    @Test
    public void testGetAllAttendance() throws Exception {
        // Перевірка отримання всіх записів відвідуваності
        TeacherDto teacherDto = TestObjectFactory.createSampleTeacher(1L);
        ClassDto _class = TestObjectFactory.createSampleClass(1L);
        StudentDto student1 = TestObjectFactory.createSampleStudent(1L,"John", "Smith", _class);
        StudentDto student2 = TestObjectFactory.createSampleStudent(2L, "Jane", "Doe", _class);

        LessonPlanDto lp = TestObjectFactory.createSampleLessonPlanDto(1L);

        LessonDto lesson = TestObjectFactory.createSampleLesson(1L, teacherDto, _class, lp);

        List<AttendanceDto> attendanceList = Arrays.asList(
                new AttendanceDto(1L, student1, lesson,true, LocalDate.of(2024, 4, 28)),
                new AttendanceDto(2L, student2, lesson,true, LocalDate.of(2024, 4, 28))
        );
        when(attendanceService.getAllAttendances()).thenReturn(attendanceList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/attendance/getAll")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(attendanceList.size()));
    }

    @Test
    public void testGetAttendanceByStudentId() throws Exception {
        // Перевірка отримання відвідуваності за ID учня
        TeacherDto teacherDto = TestObjectFactory.createSampleTeacher(1L);
        ClassDto _class = TestObjectFactory.createSampleClass(1L);
        StudentDto student1 = TestObjectFactory.createSampleStudent(1L,"John", "Smith", _class);
        StudentDto student2 = TestObjectFactory.createSampleStudent(2L, "Jane", "Doe", _class);
        LessonPlanDto lp = TestObjectFactory.createSampleLessonPlanDto(1L);
        LessonDto lesson = TestObjectFactory.createSampleLesson(1L, teacherDto, _class, lp);

        List<AttendanceDto> attendanceList = Arrays.asList(
                new AttendanceDto(1L, student1, lesson,true, LocalDate.of(2024, 4, 28)),
                new AttendanceDto(2L, student2, lesson,true, LocalDate.of(2024, 4, 28))
        );
        when(attendanceService.getAttendanceByStudentId(1L)).thenReturn(attendanceList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/attendance/student/1")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(attendanceList.size()));
    }

    @Test
    public void testGetAttendanceByLessonId() throws Exception {
        // Перевірка отримання відвідуваності за ID уроку
        TeacherDto teacherDto = TestObjectFactory.createSampleTeacher(1L);
        ClassDto _class = TestObjectFactory.createSampleClass(1L);
        StudentDto student1 = TestObjectFactory.createSampleStudent(1L,"John", "Smith", _class);
        StudentDto student2 = TestObjectFactory.createSampleStudent(2L, "Jane", "Doe", _class);

        LessonPlanDto lp = TestObjectFactory.createSampleLessonPlanDto(1L);

        LessonDto lesson = TestObjectFactory.createSampleLesson(1L, teacherDto, _class, lp);

        List<AttendanceDto> attendanceList = Arrays.asList(
                new AttendanceDto(1L, student1, lesson,true, LocalDate.of(2024, 4, 28)),
                new AttendanceDto(2L, student2, lesson,true, LocalDate.of(2024, 4, 28))
        );
        when(attendanceService.getAttendanceByLessonId(1L)).thenReturn(attendanceList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/attendance/lesson/1")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(attendanceList.size()));
    }

    @Test
    public void testDeleteAttendanceById() throws Exception {
        // Перевірка видалення запису відвідуваності за ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/attendance/delete/1")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Attendance deleted successfully"));
    }
}