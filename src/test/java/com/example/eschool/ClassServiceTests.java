package com.example.eschool;

import com.example.eschool.dto.AttendanceDto;
import com.example.eschool.dto.ClassDto;
import com.example.eschool.dto.MarkDto;
import com.example.eschool.dto.StudentReportDto;
import com.example.eschool.entities.*;
import com.example.eschool.entities.Class;
import com.example.eschool.repositories.AttendanceRepository;
import com.example.eschool.repositories.ClassRepository;
import com.example.eschool.repositories.MarkRepository;
import com.example.eschool.repositories.StudentRepository;
import com.example.eschool.services.implementation.ClassServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClassServiceTests {
    @Mock
    private ClassRepository classRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private MarkRepository markRepository;

    @InjectMocks
    private ClassServiceImpl classService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllClasses() {
        // Given
        List<Subject> subjects = new ArrayList<Subject>();
        Teacher teacher = new Teacher();
        teacher.setSubjects(subjects);
        com.example.eschool.entities.Class class1 = new Class(1L, "className1", teacher, 2012, 2023);
        Class class2 = new Class(2L, "className2", teacher, 2012, 2023);

        when(classRepository.findAll()).thenReturn(List.of(class1, class2));

        // When
        List<ClassDto> result = classService.getAllClasses();

        // Then
        assertEquals(2, result.size());
        verify(classRepository, times(1)).findAll();
    }

    @Test
    public void testGetClassById_Success() {
        // Given
        List<Subject> subjects = new ArrayList<Subject>();
        Teacher teacher = new Teacher();
        teacher.setSubjects(subjects);
        com.example.eschool.entities.Class class1 = new Class();
        class1.setTeacher(teacher);
        class1.setId(1L);

        when(classRepository.findById(1L)).thenReturn(Optional.of(class1));

        // When
        ClassDto result = classService.getClassById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(classRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetClassById_NotFound() {
        // Given
        when(classRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> {
            classService.getClassById(1L);
        });
        verify(classRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetClassAttendance() {
        // Given
        List<Subject> subjects = new ArrayList<Subject>();
        Teacher teacher = new Teacher();
        teacher.setSubjects(subjects);
        com.example.eschool.entities.Class aClass = new Class();
        aClass.setTeacher(teacher);
        aClass.setId(1L);
        when(classRepository.findById(1L)).thenReturn(Optional.of(aClass));
        Student student = new Student();
        student.setId(1L);
        student.setStudentClass(aClass);
        when(studentRepository.findStudentsByStudentClass(aClass)).thenReturn(List.of(student));
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        Lesson lesson = new Lesson();
        lesson.setClassLesson(aClass);
        attendance.setLesson(lesson);
        lesson.setTeacher(teacher);

        when(attendanceRepository.findAllByStudentId(1L)).thenReturn(List.of(attendance));

        // When
        List<AttendanceDto> result = classService.getClassAttendance(1L);

        // Then
        assertEquals(1, result.size());
        verify(classRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).findStudentsByStudentClass(aClass);
        verify(attendanceRepository, times(1)).findAllByStudentId(1L);
    }

    @Test
    public void testGetClassMarks() {
        // Given
        List<Subject> subjects = new ArrayList<Subject>();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setSubjects(subjects);
        com.example.eschool.entities.Class aClass = new Class();
        aClass.setTeacher(teacher);
        when(classRepository.findById(1L)).thenReturn(Optional.of(aClass));
        Student student = new Student();
        student.setId(1L);
        student.setStudentClass(aClass);
        when(studentRepository.findStudentsByStudentClass(aClass)).thenReturn(List.of(student));
        Mark mark = new Mark();
        mark.setStudent(student);
        LessonPlan lessonPlan = new LessonPlan();
        Subject subject = new Subject();
        List<Assignment> assignments = new ArrayList<Assignment>();
        List<Material> materials = new ArrayList<Material>();
        lessonPlan.setSubject(subject);
        lessonPlan.setAssignments(assignments);
        lessonPlan.setMaterials(materials);
        Lesson lesson = new Lesson(1L,aClass, lessonPlan, teacher);

        mark.setLesson(lesson);
        when(markRepository.findAllByStudentId(1L)).thenReturn(List.of(mark));

        // When
        List<MarkDto> result = classService.getClassMarks(1L);

        // Then
        assertEquals(1, result.size());
        verify(classRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).findStudentsByStudentClass(aClass);
        verify(markRepository, times(1)).findAllByStudentId(1L);
    }

    @Test
    public void testGenerateStudentReport() {
        // Given
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Attendance attendance1 = new Attendance();
        attendance1.setStatus(false);
        Attendance attendance2 = new Attendance();
        attendance2.setStatus(true);
        when(attendanceRepository.findAllByStudentId(1L)).thenReturn(List.of(attendance1, attendance2));

        Mark mark = new Mark();
        Lesson lesson = new Lesson();
        LessonPlan lessonPlan = new LessonPlan();
        Subject subject = new Subject();
        subject.setSubjectName("Math");
        lessonPlan.setSubject(subject);
        lesson.setLessonPlan(lessonPlan);
        mark.setLesson(lesson);
        mark.setMark(10);
        when(markRepository.findAllByStudentId(1L)).thenReturn(List.of(mark));

        // When
        StudentReportDto result = classService.generateStudentReport(1L);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getStudentName());
        assertEquals(1, result.getMissedLessons());
        assertEquals(1, result.getAttendedLessons());
        assertEquals(1, result.getAverageMarksBySubject().size());
        assertEquals(10.0, result.getAverageMarksBySubject().get("Math"));
        assertEquals(10.0, result.getAverage());
    }

    @Test
    public void testDeleteClassById() {
        // Given
        Long classId = 1L;

        // When
        classService.deleteClassById(classId);

        // Then
        verify(classRepository, times(1)).deleteById(classId);
    }
}
