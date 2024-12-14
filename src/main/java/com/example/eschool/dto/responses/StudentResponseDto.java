package com.example.eschool.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudentResponseDto {
    private String name;
    private String parentName;
    private String parentContactPhone;
    private String parentContactEmail;
}
