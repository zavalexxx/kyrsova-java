package com.example.eschool.services;

import com.example.eschool.dto.*;
import com.example.eschool.dto.responses.StudentResponseDto;

import java.util.List;

public interface ClassService {
    List<ClassDto> getAllClasses();
    ClassDto getClassById(Long id);
    List<ClassDto> getClassesByTeacher(Long teacherId);
    void deleteClassById(Long id);
    List<AttendanceDto> getClassAttendance(Long classId);
    List<MarkDto> getClassMarks(Long classId);
    StudentReportDto generateStudentReport(Long studentId);
    List<StudentResponseDto> getStudentsInfo(Long classId);
}
