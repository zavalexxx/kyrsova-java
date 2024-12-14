package com.example.eschool.dto.responses;

import com.example.eschool.dto.LessonPlanDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponseDto {
    private Long id;
    private String classLesson;
    private LessonPlanDto lessonPlan;
    private String teacher;
}