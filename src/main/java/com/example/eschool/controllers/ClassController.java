package com.example.eschool.controllers;

import com.example.eschool.dto.ClassDto;
import com.example.eschool.dto.StudentReportDto;
import com.example.eschool.dto.responses.AttendanceResponseDto;
import com.example.eschool.dto.responses.MarkResponseDto;
import com.example.eschool.dto.responses.StudentResponseDto;
import com.example.eschool.mapper.AttendanceMapper;
import com.example.eschool.mapper.MarkMapper;
import com.example.eschool.services.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@Tag(name = "Інформація про класи")
public class ClassController {
    @Autowired
    private ClassService classService;

    @Operation(
            description = "Переглянути інформацію про всі класи",
            summary = "Повертає список всіх класів в системі"
    )
    @GetMapping("/getAllClasses")
    public List<ClassDto> getAllClasses() {
        return classService.getAllClasses();
    }

    @Operation(
            description = "Переглянути інформацію про клас вчителя",
            summary = "Повертає інформацію про клас, класним керівником якого є вчитель"
    )
    @GetMapping("/get/byTeacher/{teacherId}")
    public List<ClassDto> getClassesByTeacher(@PathVariable Long teacherId) {
        return classService.getClassesByTeacher(teacherId);
    }

    @Operation(
            description = "Переглянути інформацію про клас ",
            summary = "Повертає інформацію про клас"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<ClassDto> getClassById(@PathVariable("id") Long classId) {
        ClassDto classDto = classService.getClassById(classId);
        return ResponseEntity.ok(classDto);
    }

    @Operation(
            description = "Переглянути інформацію про учнів класу",
            summary = "Повертає інформацію про учнів класу"
    )
    @GetMapping("/get/{id}/studentsInfo")
    public ResponseEntity<List<StudentResponseDto>> getStudentsInfo(@PathVariable("id") Long classId) {
          try {
              List<StudentResponseDto> students = classService.getStudentsInfo(classId);
              return ResponseEntity.ok(students);
          } catch (EntityNotFoundException e) {
              return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
          }

    }

    @Operation(
            description = "Переглянути інформацію про відвідуваність класу",
            summary = "Повертає інформацію про відвідуваність класу"
    )
    @GetMapping("/get/{classId}/attendance")
    public ResponseEntity<List<AttendanceResponseDto>> getClassAttendance(@PathVariable Long classId) {
        try {
            List<AttendanceResponseDto> attendanceDtoList = classService.getClassAttendance(classId).stream()
                    .map(AttendanceMapper::convertToResponseDto).toList();
            return ResponseEntity.ok(attendanceDtoList);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            description = "Переглянути інформацію про оцінки класу",
            summary = "Повертає інформацію про оцінки класу"
    )
    @GetMapping("/get/{classId}/marks")
    public ResponseEntity<List<MarkResponseDto>> getClassMarks(@PathVariable Long classId) {
        try {
            List<MarkResponseDto> markDtoList = classService.getClassMarks(classId).stream()
                    .map(MarkMapper::convertToResponseDto).toList();
            return ResponseEntity.ok(markDtoList);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(
            description = "Видалити клас",
            summary = "Видаляє клас з системи (тільки для адміністраторів)"
    )
    @DeleteMapping("/delete/{id}")
    public void deleteClassById(@PathVariable Long id) {
        classService.deleteClassById(id);
    }

    @Operation(
            description = "Переглянути звіт про учня",
            summary = "Повертає звіт про учня"
    )

    @GetMapping("/get/report/student/{studentId}")
    public ResponseEntity<StudentReportDto> generateStudentReport(@PathVariable Long studentId) {
        StudentReportDto studentReport = classService.generateStudentReport(studentId);
        return new ResponseEntity<>(studentReport, HttpStatus.OK);
    }
}
