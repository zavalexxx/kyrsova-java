package com.example.eschool.controllers;

import com.example.eschool.dto.AttendanceDto;
import com.example.eschool.dto.responses.AttendanceResponseDto;
import com.example.eschool.mapper.AttendanceMapper;
import com.example.eschool.services.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
class MarkAttendanceByStudentRequest {
    private Long studentId;
    private Long lessonId;
    private Boolean status;
}

@AllArgsConstructor
@RestController
@RequestMapping("/api/attendance")
@Tag(name = "Керування відвідуваністю")
public class AttendanceController {

    private AttendanceService attendanceService;

    @Operation(
            description = "Відмітити учня",
            summary = "Додає запис про присутність/відсутність учня в систему"
    )
    @PostMapping("/markByStudent")
    public ResponseEntity<String> markAttendanceByStudent (@RequestBody MarkAttendanceByStudentRequest request) {
        try {
            if (request.getStatus() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status must be either true or false");
            }
            attendanceService.markAttendanceByStudent(request.getStudentId(), request.getLessonId(), request.getStatus());
            return ResponseEntity.status(HttpStatus.OK).body("Attendance marked successfully");
        }  catch (RuntimeException e) {
            throw e;
        }

    }

    @Operation(
            description = "Переглянути інформацію про присутність/відсутність",
            summary = "Повертає список всіх записів про присутність/відсутність в системі (тільки для адміністраторів)"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<AttendanceResponseDto>> getAllAttendance() {
        List<AttendanceDto> attendance = attendanceService.getAllAttendances();
        return ResponseEntity.ok(attendance.stream().map(AttendanceMapper::convertToResponseDto).toList());
    }

    @Operation(
            description = "Переглянути інформацію про конкретну відмітку присутності/відсутності",
            summary = "Повертає запис про присутність/відсутність в системі (тільки для адміністраторів)"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<AttendanceResponseDto> getAttendanceById(@PathVariable("id") Long id) {
        AttendanceDto attendanceDto = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(AttendanceMapper.convertToResponseDto(attendanceDto));
    }

    @Operation(
            description = "Переглянути інформацію про присутність/відсутність учня",
            summary = "Повертає список всіх записів про присутність/відсутність учня в системі"
    )
    @GetMapping("/student/{id}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByStudentId(@PathVariable("id") Long id) {
        List<AttendanceDto> attendanceDto = attendanceService.getAttendanceByStudentId(id);
        return ResponseEntity.ok(attendanceDto.stream().map(AttendanceMapper::convertToResponseDto).toList());
    }

    @Operation(
            description = "Переглянути інформацію про присутність/відсутність учня на уроці",
            summary = "Повертає запис про присутність/відсутність учня на конкретному уроці в системі"
    )
    @GetMapping("/lesson/{id}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByLesson(@PathVariable("id") Long id) {
        List<AttendanceDto> attendanceDto = attendanceService.getAttendanceByLessonId(id);
        return ResponseEntity.ok(attendanceDto.stream().map(AttendanceMapper::convertToResponseDto).toList());
    }

    @Operation(
            description = "Видалити інформацію про присутність/відсутність учня на уроці",
            summary = "Видаляє запис про присутність/відсутність учня на конкретному уроці в системі"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAttendanceById(@PathVariable("id") Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.ok("Attendance deleted successfully");
    }
}
