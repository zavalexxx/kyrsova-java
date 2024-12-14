package com.example.eschool.services.implementation;

import com.example.eschool.dto.*;
import com.example.eschool.dto.responses.StudentResponseDto;
import com.example.eschool.entities.*;
import com.example.eschool.entities.Class;
import com.example.eschool.mapper.AttendanceMapper;
import com.example.eschool.mapper.ClassMapper;
import com.example.eschool.mapper.MarkMapper;
import com.example.eschool.repositories.AttendanceRepository;
import com.example.eschool.repositories.ClassRepository;
import com.example.eschool.repositories.MarkRepository;
import com.example.eschool.repositories.StudentRepository;
import com.example.eschool.services.ClassService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClassServiceImpl implements ClassService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private ClassRepository classRepository;
    private  final MarkRepository markRepository;

    /**
     * Retrieves all classes.
     *
     * @return a list of ClassDto representing all classes.
     */
    @Override
    public List<ClassDto> getAllClasses() {
        List<Class> classes = classRepository.findAll();
        return classes.stream().map(ClassMapper::mapToClassDto).toList();
    }

    /**
     * Retrieves a class by its ID.
     *
     * @param id the ID of the class.
     * @return the ClassDto representing the class.
     */
    @Override
    public ClassDto getClassById(Long id) {
        Class classes = classRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + id));
        return ClassMapper.mapToClassDto(classes);
    }

    /**
     * Retrieves information about students.
     *
     * @param classId the ID of the class.
     * @return a list of StudentResponseDto representing the students info records for the class.
     */
    @Override
    public List<StudentResponseDto> getStudentsInfo(Long classId){
            Class aClass = classRepository.findById(classId)
                    .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + classId));

            List<Student> students = studentRepository.findStudentsByStudentClass(aClass);
            if (students.isEmpty()) throw new EntityNotFoundException("Class has no students");

            return students.stream()
                    .map(student -> new StudentResponseDto(
                            student.getFirstName() + " " + student.getLastName(),
                            student.getParentName(),
                            student.getParentContactPhone(),
                            student.getParentContactEmail())).toList();
    }




    /**
     * Retrieves the attendance for a specific class.
     *
     * @param classId the ID of the class.
     * @return a list of AttendanceDto representing the attendance records for the class.
     */
    public List<AttendanceDto> getClassAttendance(Long classId) {
        try {
            Class aClass = classRepository.findById(classId)
                    .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + classId));

            List<Student> students = studentRepository.findStudentsByStudentClass(aClass);
            if (students.isEmpty()) throw new RuntimeException("Class has no students");

            List<AttendanceDto> attendanceDtoList = new ArrayList<>();
            for (Student student : students) {
                List<Attendance> studentAttendance = attendanceRepository.findAllByStudentId(student.getId());
                for (Attendance attendance : studentAttendance) {
                    AttendanceDto attendanceDto = AttendanceMapper.mapToAttendanceDto(attendance);
                    attendanceDtoList.add(attendanceDto);
                }
            }

            return attendanceDtoList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get class attendance");
        }
    }

    /**
     * Retrieves the marks for a specific class.
     *
     * @param classId the ID of the class.
     * @return a list of MarkDto representing the marks for the class.
     */
    public List<MarkDto> getClassMarks(Long classId) {
            Class aClass = classRepository.findById(classId)
                    .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + classId));

            List<Student> students = studentRepository.findStudentsByStudentClass(aClass);
            List<MarkDto> markDtoList = new ArrayList<>();
            for (Student student : students) {
                List<Mark> studentMarks = markRepository.findAllByStudentId(student.getId());
                for (Mark mark : studentMarks) {
                    MarkDto markDto = MarkMapper.mapToMarkDto(mark);
                    markDtoList.add(markDto);
                }
            }
            return markDtoList;
    }

    /**
     * Retrieves all classes taught by a specific teacher.
     *
     * @param teacherId the ID of the teacher.
     * @return a list of ClassDto representing the classes taught by the teacher.
     */
    @Override
    public List<ClassDto> getClassesByTeacher(Long teacherId) {
        try {
            List<Class> classes = classRepository.findByTeacherId(teacherId);
            return classes.stream().map(ClassMapper::mapToClassDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get classes by teacher");
        }
    }

    /**
     * Generates a report for a specific student.
     *
     * @param studentId the ID of the student.
     * @return a StudentReportDto representing the student's report.
     */
    public StudentReportDto generateStudentReport(Long studentId) {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

            List<Attendance> attendanceList = attendanceRepository.findAllByStudentId(studentId);
            List<Mark> markList = markRepository.findAllByStudentId(studentId);

            long totalLessons = attendanceList.size();
            long missedLessons = attendanceList.stream().filter(attendance -> !attendance.getStatus()).count();

            Map<String, List<Mark>> marksBySubject = markList.stream()
                    .collect(Collectors.groupingBy(mark -> mark.getLesson().getLessonPlan().getSubject().getSubjectName()));
            Map<String, Double> averageMarksBySubject = marksBySubject.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> entry.getValue().stream().mapToInt(Mark::getMark).average().orElse(0.0)));

            Double average = markList.stream().mapToInt(Mark::getMark).average().orElse(0.0);

            return new StudentReportDto(student.getFirstName() + " " + student.getLastName(), missedLessons, totalLessons - missedLessons, averageMarksBySubject, average);
    }

    /**
     * Deletes a class by its ID.
     *
     * @param id the ID of the class to be deleted.
     */
    @Override
    public void deleteClassById(Long id) {
        try {
            classRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete attendance");
        }
    }
}


