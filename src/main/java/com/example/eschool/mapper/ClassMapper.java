package com.example.eschool.mapper;

import com.example.eschool.dto.ClassDto;
import com.example.eschool.entities.Class;

public class ClassMapper {
    public static ClassDto mapToClassDto (Class classes){
        return new ClassDto(
                classes.getId(),
                classes.getClassName(),
                TeacherMapper.mapToTeacherDto(classes.getTeacher()),
                classes.getYearStart(),
                classes.getYearStart()
        );
    }

    public static Class mapToClass (ClassDto classDto){
        return new Class(
                classDto.getId(),
                classDto.getClassName(),
                TeacherMapper.mapToTeacher(classDto.getTeacherDto()),
                classDto.getYearStart(),
                classDto.getYearStart()
        );
    }
}
