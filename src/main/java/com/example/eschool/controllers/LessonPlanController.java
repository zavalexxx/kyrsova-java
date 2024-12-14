package com.example.eschool.controllers;

import com.example.eschool.dto.LessonPlanDto;
import com.example.eschool.services.LessonPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
class LessonPlanRequest {
    private String topic;
    private String description;
    private Long subjectId;
    private List<Long> assignmentIds;
    private List<Long> materialIds;
}

@NoArgsConstructor
@RestController
@AllArgsConstructor
@RequestMapping("/api/lesson_plans")
@Tag(name = "Керування планами уроків")
public class LessonPlanController {
    @Autowired
    private LessonPlanService lessonPlanService;

    @Operation(
            description = "Додати новий план уроку",
            summary = "Додає в систему новий план уроку"
    )
    @PostMapping("/add")
    public ResponseEntity<LessonPlanDto> createLessonPlan(@RequestBody LessonPlanRequest request) {
        try {
            LessonPlanDto createdLessonPlan = lessonPlanService.createLessonPlan(request.getTopic(), request.getDescription(), request.getSubjectId(), request.getAssignmentIds(), request.getMaterialIds());
            return new ResponseEntity<>(createdLessonPlan, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw e;
        }

    }

    @Operation(
            description = "Переглянути інформацію про план уроку",
            summary = "Повертає інформацію про конкретний план уроку"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<LessonPlanDto> getLessonPlanById(@PathVariable Long id) {
        LessonPlanDto lessonPlanDto = lessonPlanService.getLessonPlanById(id);
        return new ResponseEntity<>(lessonPlanDto, HttpStatus.OK);
    }

    @Operation(
            description = "Переглянути інформацію про всі плани уроків",
            summary = "Повертає список всіх планів уроків в системі"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<LessonPlanDto>> getAllLessonPlans() {
        List<LessonPlanDto> lessonPlans = lessonPlanService.getAllLessonPlans();
        return new ResponseEntity<>(lessonPlans, HttpStatus.OK);
    }

    @Operation(
            description = "Оновити інформацію про план уроку",
            summary = "Оновлення інформації про конкретний план уроку"
    )
    @PutMapping("/update/{lessonId}")
    public ResponseEntity<LessonPlanDto> updateLessonPlan(
            @PathVariable Long lessonId,
            @RequestBody LessonPlanRequest request) {
        LessonPlanDto updatedLessonPlan = lessonPlanService.updateLessonPlan(lessonId, request.getTopic(), request.getDescription(), request.getSubjectId(), request.getAssignmentIds(), request.getMaterialIds());
        return new ResponseEntity<>(updatedLessonPlan, HttpStatus.OK);
    }

    @Operation(
            description = "Видалити план уроку",
            summary = "Видаляє план уроку з системи"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLessonPlan(@PathVariable Long id) {
        lessonPlanService.deleteLessonPlan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
