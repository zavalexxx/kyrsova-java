package com.example.eschool.mapper;

import com.example.eschool.dto.LessonDto;
import com.example.eschool.dto.responses.LessonResponseDto;
import com.example.eschool.entities.Lesson;

public class LessonMapper {
    public static LessonDto mapToLessonDto (Lesson lesson){
        return new LessonDto(
                lesson.getLessonId(),
                ClassMapper.mapToClassDto(lesson.getClassLesson()),
                LessonPlanMapper.mapToLessonPlanDto(lesson.getLessonPlan()),
                TeacherMapper.mapToTeacherDto(lesson.getTeacher())
        );
    }

    public static Lesson mapToLesson (LessonDto lessonDto){
        return new Lesson(
                lessonDto.getId(),
                ClassMapper.mapToClass(lessonDto.getClassLesson()),
                LessonPlanMapper.mapToLessonPlan(lessonDto.getLessonPlan()),
                TeacherMapper.mapToTeacher(lessonDto.getTeacher())
        );
    }


    public static LessonResponseDto convertToResponseDto(LessonDto lessonDto) {
        return new LessonResponseDto (
                lessonDto.getId(),
                lessonDto.getClassLesson().getClassName(),
                lessonDto.getLessonPlan(),
                lessonDto.getTeacher().getFirstName() + " " + lessonDto.getTeacher().getLastName()
        );
    }
}
