package com.example.eschool.services;

import com.example.eschool.dto.LessonPlanDto;

import java.util.List;

public interface LessonPlanService {
    LessonPlanDto createLessonPlan(String topic, String description, Long subjectId, List<Long> assignmentIds, List<Long> materialIds);
    LessonPlanDto getLessonPlanById(Long id);
    List<LessonPlanDto> getAllLessonPlans();
    LessonPlanDto updateLessonPlan(Long lessonPlanId, String topic, String description, Long subjectId, List<Long> assignmentIds, List<Long> materialIds);
    void deleteLessonPlan(Long id);
}
