package com.example.eschool;
import com.example.eschool.dto.*;
import com.example.eschool.dto.responses.StudentResponseDto;
import com.example.eschool.services.ClassService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClassIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClassService classService;

    @Test
    public void testGetClassById_Success() throws Exception {
        // Перевірка успішного отримання класу за ідентифікатором.
        ClassDto classDto = TestObjectFactory.createSampleClass(1L);
        classDto.setClassName("5A");

        when(classService.getClassById(1L)).thenReturn(classDto);
        mockMvc.perform(get("/api/classes/get/{id}", 1L)
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.className").value("5A"));
    }

    @Test
    public void testGetClassById_NotFound() throws Exception {
        // Перевірка випадку, коли клас не знайдено.
        when(classService.getClassById(999L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/classes/get/{id}", 999L)
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGenerateStudentReport_Success() throws Exception {
        // Перевірка успішного генерування звіту про учня.
        StudentReportDto studentReportDto = new StudentReportDto();
        studentReportDto.setStudentName("John Doe");
        studentReportDto.setMissedLessons(2);

        when(classService.generateStudentReport(1L)).thenReturn(studentReportDto);
        mockMvc.perform(get("/api/classes/get/report/student/{studentId}", 1L)
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentName").value("John Doe"))
                .andExpect(jsonPath("$.missedLessons").value(2));
    }

    @Test
    public void testGenerateStudentReport_NotFound() throws Exception {
        // Перевірка випадку, коли звіт про учня не знайдено.
        when(classService.generateStudentReport(999L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/classes/get/report/student/{studentId}", 999L)
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetStudentsInfo_Success() throws Exception {
        // Перевірка успішного отримання інформації про студентів.
        ClassDto _class = TestObjectFactory.createSampleClass(1L);
        StudentDto studentDto = TestObjectFactory.createSampleStudent(1L, "Jane", "Doe", _class);
        studentDto.setParentName("John Doe");
        studentDto.setParentContactPhone("123-456-7890");
        studentDto.setParentContactEmail("john.doe@example.com");

        StudentResponseDto responseDto = new StudentResponseDto(studentDto.getFirstName() + " " + studentDto.getLastName(),
                studentDto.getParentName(),
                studentDto.getParentContactPhone(),
                studentDto.getParentContactEmail());

        when(classService.getStudentsInfo(1L)).thenReturn(Collections.singletonList(responseDto));

        mockMvc.perform(get("/api/classes/get/{id}/studentsInfo", 1L)
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].parentName").value("John Doe"))
                .andExpect(jsonPath("$[0].parentContactPhone").value("123-456-7890"))
                .andExpect(jsonPath("$[0].parentContactEmail").value("john.doe@example.com"));
    }

    @Test
    public void testGetStudentsInfo_NotFound() throws Exception {
        // Перевірка випадку, коли інформацію про студентів не знайдено.
        when(classService.getStudentsInfo(999L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/classes/get/{id}/studentsInfo", 999L)
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllClasses_Success() throws Exception {
        // Перевірка успішного отримання всіх класів.
        ClassDto class1 = TestObjectFactory.createSampleClass(1L);
        ClassDto class2 = TestObjectFactory.createSampleClass(2L);
        ClassDto class3 = TestObjectFactory.createSampleClass(3L);

        List<ClassDto> classList = Arrays.asList(class1, class2, class3);

        when(classService.getAllClasses()).thenReturn(classList);
        mockMvc.perform(get("/api/classes/getAllClasses")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(classList.size()));
    }

    @Test
    public void testGetClassAttendance_Success() throws Exception {
        // Перевірка успішного отримання списку відвідуваності класу.
        TeacherDto teacherDto = TestObjectFactory.createSampleTeacher(1L);
        ClassDto _class = TestObjectFactory.createSampleClass(1L);
        StudentDto student1 = TestObjectFactory.createSampleStudent(1L,"John", "Smith", _class);
        StudentDto student2 = TestObjectFactory.createSampleStudent(2L, "Jane", "Doe", _class);
        LessonPlanDto lp = TestObjectFactory.createSampleLessonPlanDto(1L);
        LessonDto lesson = TestObjectFactory.createSampleLesson(1L, teacherDto, _class, lp);

        List<AttendanceDto> attendanceDtoList = Arrays.asList(
                new AttendanceDto(1L, student1, lesson,true, LocalDate.of(2024, 4, 28)),
                new AttendanceDto(2L, student2, lesson,true, LocalDate.of(2024, 4, 28))
        );

        when(classService.getClassAttendance(1L)).thenReturn(attendanceDtoList);

        mockMvc.perform(get("/api/classes/get/{classId}/attendance", 1L)
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(attendanceDtoList.size()));
    }

    @Test
    public void testGetClassAttendance_NotFound() throws Exception {
        // Перевірка випадку, коли список відвідуваності класу не знайдено.
        when(classService.getClassAttendance(999L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/classes/get/{classId}/attendance", 999L)
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetClassMarks_NotFound() throws Exception {
        // Перевірка випадку, коли оцінки для класу не знайдено.
        when(classService.getClassMarks(999L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/classes/get/{classId}/marks", 999L)
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
