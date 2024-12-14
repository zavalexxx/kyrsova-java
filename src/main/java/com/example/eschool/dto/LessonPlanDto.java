package com.example.eschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonPlanDto {
    private Long lessonId;
    private String topic;
    private String description;
    private SubjectDto subject;
    private List<AssignmentDto> assignments;
    private List<MaterialDto> materials;
}
