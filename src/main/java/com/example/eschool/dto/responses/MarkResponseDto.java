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
public class MarkResponseDto {
    private String studentName;
    private LocalDate date;
    private String subjectName;
    private Integer mark;
}
