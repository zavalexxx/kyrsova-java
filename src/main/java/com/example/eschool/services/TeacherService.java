package com.example.eschool.services;

import com.example.eschool.dto.TeacherDto;

import java.util.List;

public interface TeacherService {
    TeacherDto createTeacher(TeacherDto teacherDto);
    TeacherDto getTeacherById(Long teacherId);
    List<TeacherDto> getAllTeachers();
    TeacherDto updateTeacher(Long teacherId, TeacherDto updatedTeacher);
    void deleteTeacher(Long teacherId);
}
