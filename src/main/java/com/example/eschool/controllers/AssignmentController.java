package com.example.eschool.controllers;

import com.example.eschool.dto.AssignmentDto;
import com.example.eschool.services.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("/api/assignments")
@Tag(name = "Керування завданнями")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;

    @Operation(
            description = "Додати нове завдання",
            summary = "Додає в систему нове завдання"
    )
    @PostMapping("/add")
    public ResponseEntity<AssignmentDto> createAssignment(@RequestBody AssignmentDto assignmentDto) {
        AssignmentDto createdAssignment = assignmentService.createAssignment(assignmentDto);
        return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
    }

    @Operation(
            description = "Переглянути інформацію про завдання",
            summary = "Повертає інформацію про конкретне завдання"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<AssignmentDto> getAssignmentById(@PathVariable Long id) {
        AssignmentDto assignmentDto = assignmentService.getAssignmentById(id);
        return new ResponseEntity<>(assignmentDto, HttpStatus.OK);
    }

    @Operation(
            description = "Переглянути інформацію про всі завдання",
            summary = "Повертає список всіх завдань в системі"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<AssignmentDto>> getAllAssignments() {
        List<AssignmentDto> assignments = assignmentService.getAllAssignments();
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @Operation(
            description = "Оновити інформацію про завдання",
            summary = "Оновлення інформації про завдання"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<AssignmentDto> updateAssignment(@PathVariable Long id, @RequestBody AssignmentDto assignmentDto) {
        AssignmentDto updatedAssignment = assignmentService.updateAssignment(id, assignmentDto);
        return new ResponseEntity<>(updatedAssignment, HttpStatus.OK);
    }

    @Operation(
            description = "Видалити завдання",
            summary = "Видаляє завдання з системи"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
