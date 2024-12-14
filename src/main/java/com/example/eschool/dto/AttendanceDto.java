package com.example.eschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class AttendanceDto {
    private Long id;
    private StudentDto student;
    private LessonDto lessonDto;
    private Boolean status;
    private LocalDate date;
}

