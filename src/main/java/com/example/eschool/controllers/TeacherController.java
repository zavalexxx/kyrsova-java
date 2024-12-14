package com.example.eschool.controllers;

import com.example.eschool.dto.TeacherDto;
import com.example.eschool.services.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Інформація про вчителів")
public class TeacherController {

    private TeacherService teacherService;

    @Operation(
            description = "Додати нового вчителя",
            summary = "Додає в систему нового вчителя (тільки для адміністраторів)"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto teacherDto) {
        TeacherDto savedTeacher = teacherService.createTeacher(teacherDto);
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
    }

    @Operation(
            description = "Переглянути інформацію про вчителя",
            summary = "Повертає інформацію про конкретного вчителя (тільки для адміністраторів)"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/get/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable("id") Long teacherId) {
        TeacherDto teacherDto = teacherService.getTeacherById(teacherId);
        return ResponseEntity.ok(teacherDto);
    }

    @Operation(
            description = "Переглянути інформацію про всіх вчителів",
            summary = "Повертає список всіх зареєстрованих в системі вчителів (тільки для адміністраторів)"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        List<TeacherDto> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @Operation(
            description = "Оновити інформацію про вчителя",
            summary = "Оновлення інформації про конкретного вчителя (тільки для адміністраторів)"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable("id") Long teacherId,
                                                    @RequestBody TeacherDto updatedTeacher) {
        TeacherDto teacherDto = teacherService.updateTeacher(teacherId, updatedTeacher);
        return ResponseEntity.ok(teacherDto);
    }

    @Operation(
            description = "Видалити вчителя",
            summary = "Видаляє вчителя з системи (тільки для адміністраторів)"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeacherById(@PathVariable("id") Long teacherId) {
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.ok("Teacher deleted successfully");
    }
}

