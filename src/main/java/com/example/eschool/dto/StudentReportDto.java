package com.example.eschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentReportDto {
    private String studentName;
    private long missedLessons;
    private long attendedLessons;
    private Map<String, Double> averageMarksBySubject;
    private Double average;
}
