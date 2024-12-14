package com.example.eschool;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.example.eschool.entities.Attendance;
import com.example.eschool.entities.Lesson;
import com.example.eschool.entities.Student;
import com.example.eschool.repositories.AttendanceRepository;
import com.example.eschool.repositories.LessonRepository;
import com.example.eschool.repositories.StudentRepository;
import com.example.eschool.services.implementation.AttendanceServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @Test
    public void testAddAttendance_Success() {
        // Mock data
        Long studentId = 123L;
        Long lessonId = 456L;
        Boolean presence = true;
        Student student = new Student();
        Lesson lesson = new Lesson();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        attendanceService.markAttendanceByStudent(studentId, lessonId, presence);
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    public void testAddAttendance_StudentNotFound() {
        Long studentId = 123L;
        Long lessonId = 456L;
        Boolean presence = true;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            attendanceService.markAttendanceByStudent(studentId, lessonId, presence);
        });
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    public void testAddAttendance_LessonNotFound() {
        Long studentId = 123L;
        Long lessonId = 456L;
        Boolean presence = true;
        Student student = new Student();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            attendanceService.markAttendanceByStudent(studentId, lessonId, presence);
        });
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }
}