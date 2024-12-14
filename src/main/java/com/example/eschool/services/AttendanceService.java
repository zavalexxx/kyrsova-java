package com.example.eschool.services;

import com.example.eschool.dto.AttendanceDto;

import java.util.List;

public interface AttendanceService {
    void markAttendanceByStudent(Long studentId, Long lessonId, Boolean status);
    AttendanceDto getAttendanceById(Long attendanceId);
    List<AttendanceDto> getAttendanceByStudentId(Long studentId);
    List<AttendanceDto> getAllAttendances();
    void deleteAttendance(Long attendanceId);
    List<AttendanceDto> getAttendanceByLessonId(Long lessonId);

}
