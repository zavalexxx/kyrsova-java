package com.example.eschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarkDto {
    private Long id;
    private StudentDto studentDto;
    private LessonDto lessonDto;
    private LocalDate date;
    private Integer mark;
}
