package com.example.eschool.services;

import com.example.eschool.dto.LessonDto;

import java.util.List;

public interface LessonService {
    List<String> getListOfClassStudents(Long lessonId);
    List<LessonDto> getAllLessonPlans();
    LessonDto getLessonById(Long lessonId);
    LessonDto createLesson(String className, Long teacherId, Long lessonPlanId);
    LessonDto editLesson(Long lessonId, String className, Long teacherId, Long lessonPlanId);
    void deleteLesson(Long lessonId);
}
