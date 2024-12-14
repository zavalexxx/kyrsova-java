package com.example.eschool.controllers;

import com.example.eschool.dto.MarkDto;
import com.example.eschool.dto.responses.MarkResponseDto;
import com.example.eschool.mapper.MarkMapper;
import com.example.eschool.services.MarkService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Getter
class MarkRequest {
    private Long studentId;
    private Long lessonId;
    private Integer mark;
}

@AllArgsConstructor
@RestController
@RequestMapping("/api/marks")
@Tag(name = "Керування оцінками")
public class MarkController {

    public MarkService markService;

    @GetMapping("/{id}")
    @Hidden
    public ResponseEntity<MarkResponseDto> getMarkById(@PathVariable("id") Long id) {
        MarkDto markDto = markService.getMarkById(id);
        return ResponseEntity.ok(MarkMapper.convertToResponseDto(markDto));
    }

    @Operation(
            description = "Переглянути оцінки учня",
            summary = "Повертає всі оцінки учня"
    )
    @GetMapping("/student/{id}")
    public ResponseEntity<List<MarkResponseDto>> getMarkByStudentId(@PathVariable("id") Long id) {
        List<MarkDto> markDto = markService.getMarksByStudentId(id);
        return ResponseEntity.ok(markDto.stream().map(MarkMapper::convertToResponseDto).toList());
    }

    @Operation(
            description = "Додати нову оцінку",
            summary = "Додає в систему нову оцінку"
    )
    @PostMapping("/addMark")
    public ResponseEntity<String> addMark (@RequestBody MarkRequest request) {
        try {
            markService.markStudent(request.getStudentId(), request.getLessonId(), request.getMark());
            return ResponseEntity.status(HttpStatus.OK).body("Mark added successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson or student not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid mark value");
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @Operation(
            description = "Видалити оцінку",
            summary = "Видаляє оцінку з системи"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMarkById(@PathVariable("id") Long markId) {
        markService.deleteMark(markId);
        return ResponseEntity.ok("Mark deleted successfully");
    }

}
