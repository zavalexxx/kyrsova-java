package com.example.eschool.mapper;


import com.example.eschool.dto.LessonPlanDto;
import com.example.eschool.entities.LessonPlan;

public class LessonPlanMapper {
    public static LessonPlanDto mapToLessonPlanDto (LessonPlan lessonPlan){
        if (lessonPlan == null) {
            return null;
        }
        return new LessonPlanDto(
                lessonPlan.getLessonId(),
                lessonPlan.getTopic(),
                lessonPlan.getDescription(),
                SubjectMapper.mapToSubjectDto(lessonPlan.getSubject()),
                lessonPlan.getAssignments().stream().map(AssignmentMapper::mapToAssignmentDto).toList(),
                lessonPlan.getMaterials().stream().map(MaterialMapper::mapToMaterialDto).toList()

        );
    }


    public static LessonPlan mapToLessonPlan(LessonPlanDto lessonPlanDto) {
        if (lessonPlanDto == null) {
            return null;
        } return new LessonPlan(
                lessonPlanDto.getLessonId(),
                lessonPlanDto.getTopic(),
                lessonPlanDto.getDescription(),
                SubjectMapper.mapToSubject(lessonPlanDto.getSubject()),
                lessonPlanDto.getAssignments().stream().map(AssignmentMapper::mapToAssignment).toList(),
                lessonPlanDto.getMaterials().stream().map(MaterialMapper::mapToMaterial).toList()
        );

    }
}

