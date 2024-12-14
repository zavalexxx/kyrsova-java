package com.example.eschool.mapper;

import com.example.eschool.dto.StudentDto;
import com.example.eschool.entities.Student;

public class StudentMapper {
    public static StudentDto mapToStudentDto (Student student){
        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                ClassMapper.mapToClassDto(student.getStudentClass()),
                student.getParentName(),
                student.getParentContactPhone(),
                student.getParentContactEmail()
        );
    }

    public static Student mapToStudent (StudentDto studentDto){
        return new Student(
                studentDto.getId(),
                studentDto.getFirstName(),
                studentDto.getLastName(),
                ClassMapper.mapToClass(studentDto.getStudentClass()),
                studentDto.getParentName(),
                studentDto.getParentContactPhone(),
                studentDto.getParentContactEmail()
        );
    }
}
