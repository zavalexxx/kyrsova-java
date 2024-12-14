package com.example.eschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private Long id;
    private ClassDto classLesson;
    private LessonPlanDto lessonPlan;
    private TeacherDto teacher;
}
