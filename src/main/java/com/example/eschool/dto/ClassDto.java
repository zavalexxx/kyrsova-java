package com.example.eschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto {
    private Long id;
    private String className;
    private TeacherDto teacherDto;
    private Integer yearStart;
    private Integer yearFinish;
}
