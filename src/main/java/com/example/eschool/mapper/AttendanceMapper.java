package com.example.eschool.mapper;

import com.example.eschool.dto.AttendanceDto;
import com.example.eschool.dto.responses.AttendanceResponseDto;
import com.example.eschool.entities.Attendance;

public class AttendanceMapper {
    public static AttendanceDto mapToAttendanceDto (Attendance attendance){
        return new AttendanceDto(
                attendance.getId(),
                StudentMapper.mapToStudentDto(attendance.getStudent()),
                LessonMapper.mapToLessonDto(attendance.getLesson()),
                attendance.getStatus(),
                attendance.getDate()
        );
    }

    public static Attendance mapToAttendance (AttendanceDto attendanceDto){
        return new Attendance(
                attendanceDto.getId(),
                StudentMapper.mapToStudent(attendanceDto.getStudent()),
                LessonMapper.mapToLesson(attendanceDto.getLessonDto()),
                attendanceDto.getStatus(),
                attendanceDto.getDate()
        );
    }

    public static AttendanceResponseDto convertToResponseDto(AttendanceDto attendanceDto) {
        AttendanceResponseDto responseDto = new AttendanceResponseDto();
        responseDto.setStudentName(attendanceDto.getStudent().getFirstName() + " " + attendanceDto.getStudent().getLastName());
        responseDto.setStudentClass(attendanceDto.getStudent().getStudentClass().getClassName());
        responseDto.setPresence(attendanceDto.getStatus());
        responseDto.setDate(attendanceDto.getDate());
        responseDto.setLessonTeacherName(attendanceDto.getLessonDto().getTeacher().getFirstName() + " " + attendanceDto.getLessonDto().getTeacher().getLastName());
        responseDto.setLessonTopic(attendanceDto.getLessonDto().getLessonPlan().getTopic());
        return responseDto;
    }
}
