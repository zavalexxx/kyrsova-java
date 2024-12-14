package com.example.eschool.services;

import com.example.eschool.dto.MarkDto;

import java.util.List;

public interface MarkService {
    List<MarkDto> getMarksByStudentId(Long studentId);
    MarkDto getMarkById(Long markId);
    void markStudent(Long studentId, Long lessonId, Integer markValue);
    void deleteMark(Long markId);
}
