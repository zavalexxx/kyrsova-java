package com.example.eschool.controllers;

import com.example.eschool.dto.LessonDto;
import com.example.eschool.services.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
class LessonRequest {
    private String className;
    private Long teacherId;
    private Long lessonPlanId;
}

@NoArgsConstructor
@RestController
@AllArgsConstructor
@RequestMapping("/api/lessons")
@Tag(name = "Інформація про уроки")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @Operation(
            description = "Переглянути список учнів",
            summary = "Повертає список учнів, які мають бути присутні на уроці"
    )
    @GetMapping("/{lessonId}/students")
    public ResponseEntity<List<String>> getListOfClassStudents(@PathVariable Long lessonId) {
        List<String> students = lessonService.getListOfClassStudents(lessonId);
        return ResponseEntity.ok(students);
    }

    @Operation(
            description = "Переглянути інформацію про урок",
            summary = "Повертає інформацію про конкретний урок"
    )
    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long lessonId) {
        LessonDto lessonDto = lessonService.getLessonById(lessonId);
        return ResponseEntity.ok(lessonDto);
    }

    @Operation(
            description = "Переглянути інформацію про всі уроки",
            summary = "Повертає список всіх уроків в системі"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<LessonDto>> getAllLessons() {
        List<LessonDto> lessons = lessonService.getAllLessonPlans();
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @Operation(
            description = "Додати новий урок",
            summary = "Додає в систему новий урок"
    )
    @PostMapping("/add")
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonRequest lesson) {
        LessonDto createdLesson = lessonService.createLesson(lesson.getClassName(), lesson.getTeacherId(), lesson.getLessonPlanId());
        return new ResponseEntity<>(createdLesson, HttpStatus.CREATED);
    }

    @Operation(
            description = "Оновити інформацію про урок",
            summary = "Оновлення інформації про конкретний урок"
    )
    @PutMapping("/update/{lessonId}")
    public ResponseEntity<LessonDto> editLesson(@PathVariable Long lessonId, @RequestBody LessonRequest lesson) {
        LessonDto editedLesson = lessonService.editLesson(lessonId, lesson.getClassName(), lesson.getTeacherId(), lesson.getLessonPlanId());
        return ResponseEntity.ok(editedLesson);
    }

    @Operation(
            description = "Видалити урок",
            summary = "Видаляє урок з системи"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
