package com.example.eschool.services.implementation;

import com.example.eschool.dto.AttendanceDto;
import com.example.eschool.entities.Attendance;
import com.example.eschool.entities.Lesson;
import com.example.eschool.entities.Student;
import com.example.eschool.mapper.AttendanceMapper;
import com.example.eschool.repositories.AttendanceRepository;
import com.example.eschool.repositories.LessonRepository;
import com.example.eschool.repositories.StudentRepository;
import com.example.eschool.services.AttendanceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@Service
public class AttendanceServiceImpl implements AttendanceService {

    private AttendanceRepository attendanceRepository;
    private StudentRepository studentRepository;
    private LessonRepository lessonRepository;

    /**
     * Marks attendance for a student in a lesson.
     *
     * @param studentId The ID of the student.
     * @param lessonId  The ID of the lesson.
     * @param status    The attendance status.
     * @throws EntityNotFoundException If the student or lesson is not found.
     */
    @Override
    public void markAttendanceByStudent(Long studentId, Long lessonId, Boolean status) {
        try {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));

            if (status == null) {
                throw new IllegalArgumentException("Значення мають бути true або false");
            }

            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setLesson(lesson);

            attendance.setDate(LocalDate.now());

            attendanceRepository.save(attendance);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to mark attendance by student");
        }
    }

    /**
     * Retrieves attendance by ID.
     *
     * @param attendanceId The ID of the attendance.
     * @return The DTO of the retrieved attendance.
     */
    @Override
    public AttendanceDto getAttendanceById(Long attendanceId) {
        try {
            Attendance attendance = attendanceRepository.findById(attendanceId)
                    .orElseThrow(() -> new EntityNotFoundException("Attendance not found with ID: " + attendanceId));
            return AttendanceMapper.mapToAttendanceDto(attendance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get attendance by ID");
        }
    }

    /**
     * Retrieves all attendances.
     *
     * @return The list of attendance DTOs.
     */
    @Override
    public List<AttendanceDto> getAllAttendances() {
        try {
            List<Attendance> attendances = attendanceRepository.findAll();
            return attendances.stream().map(AttendanceMapper::mapToAttendanceDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all attendances");
        }
    }

    /**
     * Retrieves attendance for a student by student ID.
     *
     * @param studentId The ID of the student.
     * @return The list of attendance DTOs for the student.
     */
    @Override
    public List<AttendanceDto> getAttendanceByStudentId(Long studentId) {
        try {
            return attendanceRepository.findAllByStudentId(studentId).stream()
                    .map(AttendanceMapper::mapToAttendanceDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get attendance by student ID");
        }
    }

    /**
     * Retrieves attendance for a lesson by lesson ID.
     *
     * @param lessonId The ID of the lesson.
     * @return The list of attendance DTOs for the lesson.
     */
    @Override
    public List<AttendanceDto> getAttendanceByLessonId(Long lessonId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));

            return attendanceRepository.findByLesson(lesson).stream()
                    .map(AttendanceMapper::mapToAttendanceDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get attendance by lesson ID");
        }
    }

    /**
     * Deletes attendance by ID.
     *
     * @param attendanceId The ID of the attendance to delete.
     */
    @Override
    public void deleteAttendance(Long attendanceId) {
        try {
            attendanceRepository.deleteById(attendanceId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete attendance");
        }
    }
}
