package com.example.eschool;
import com.example.eschool.dto.MarkDto;
import com.example.eschool.entities.*;
import com.example.eschool.entities.Class;
import com.example.eschool.repositories.LessonRepository;
import com.example.eschool.repositories.MarkRepository;
import com.example.eschool.repositories.StudentRepository;
import com.example.eschool.services.implementation.MarkServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MarkServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private MarkRepository markRepository;

    @InjectMocks
    private MarkServiceImpl markService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMarkById_Success() {
        // Given
        Long markId = 1L;
        Mark mark = new Mark();
        mark.setId(markId);
        mark.setMark(10);
        Class aclass = new Class();
        List<Subject> subjects = new ArrayList<>();
        Teacher teacher = new Teacher();
        teacher.setSubjects(subjects);
        aclass.setTeacher(teacher);
        Student student = new Student();
        student.setStudentClass(aclass);
        Lesson lesson = new Lesson();
        lesson.setClassLesson(aclass);
        lesson.setTeacher(teacher);
        LessonPlan lessonPlan = new LessonPlan();
        Subject subject = new Subject();
        List<Assignment> assignments = new ArrayList<Assignment>();
        List<Material> materials = new ArrayList<Material>();
        lessonPlan.setSubject(subject);
        lessonPlan.setAssignments(assignments);
        lessonPlan.setMaterials(materials);
        lesson.setLessonPlan(lessonPlan);
        mark.setLesson(lesson);
        mark.setStudent(student);
        when(markRepository.findById(markId)).thenReturn(Optional.of(mark));

        // When
        MarkDto result = markService.getMarkById(markId);

        // Then
        assertEquals(mark.getId(), result.getId());
        assertEquals(mark.getMark(), result.getMark());
    }

    @Test
    public void testGetMarkById_NotFound() {
        // Given
        Long markId = 1L;
        when(markRepository.findById(markId)).thenReturn(Optional.empty());

        // Then
        assertThrows(RuntimeException.class, () -> markService.getMarkById(markId));
    }

    @Test
    public void testGetMarksByStudentId_Success() {
        // Given
        Long studentId = 1L;
        Class aclass = new Class();
        List<Subject> subjects = new ArrayList<>();
        Teacher teacher = new Teacher();
        teacher.setSubjects(subjects);
        aclass.setTeacher(teacher);
        Student student = new Student();
        student.setStudentClass(aclass);
        Lesson lesson = new Lesson();
        lesson.setClassLesson(aclass);
        lesson.setTeacher(teacher);
        LessonPlan lessonPlan = new LessonPlan();
        Subject subject = new Subject();
        List<Assignment> assignments = new ArrayList<Assignment>();
        List<Material> materials = new ArrayList<Material>();
        lessonPlan.setSubject(subject);
        lessonPlan.setAssignments(assignments);
        lessonPlan.setMaterials(materials);
        lesson.setLessonPlan(lessonPlan);
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setLesson(lesson);
        mark1.setStudent(student);
        Mark mark2 = new Mark();
        mark2.setId(2L);
        mark2.setStudent(student);
        mark2.setLesson(lesson);
        when(markRepository.findAllByStudentId(studentId)).thenReturn(List.of(mark1, mark2));

        // When
        List<MarkDto> result = markService.getMarksByStudentId(studentId);

        // Then
        assertEquals(2, result.size());
    }

    @Test
    public void testMarkStudent_Success() {
        // Given
        Long studentId = 1L;
        Long lessonId = 1L;
        Integer markValue = 10;
        Student student = new Student();
        student.setId(studentId);
        Lesson lesson = new Lesson();
        lesson.setLessonId(lessonId);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        // When
        markService.markStudent(studentId, lessonId, markValue);

        // Then
        verify(markRepository, times(1)).save(any(Mark.class));
    }

    @Test
    public void testMarkStudent_InvalidMarkValue() {
        // Given
        Long studentId = 1L;
        Long lessonId = 1L;
        Integer invalidMarkValue = 15;

        // Then
        assertThrows(IllegalArgumentException.class, () -> markService.markStudent(studentId, lessonId, invalidMarkValue));
    }

    @Test
    public void testMarkStudent_StudentNotFound() {
        // Given
        Long studentId = 1L;
        Long lessonId = 1L;
        Integer markValue = 10;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Then
        assertThrows(EntityNotFoundException.class, () -> markService.markStudent(studentId, lessonId, markValue));
    }

    @Test
    public void testMarkStudent_LessonNotFound() {
        // Given
        Long studentId = 1L;
        Long lessonId = 1L;
        Integer markValue = 10;
        Student student = new Student();
        student.setId(studentId);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        // Then
        assertThrows(EntityNotFoundException.class, () -> markService.markStudent(studentId, lessonId, markValue));
    }

}