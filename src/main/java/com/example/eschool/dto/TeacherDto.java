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
public class TeacherDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    //private String password;
    private List<SubjectDto> subjects;
}
