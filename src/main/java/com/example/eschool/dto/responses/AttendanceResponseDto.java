package com.example.eschool.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponseDto {
    private String studentName;
    private String studentClass;
    private Boolean presence;
    private LocalDate date;
    private String lessonTeacherName;
    private String lessonTopic;
}
