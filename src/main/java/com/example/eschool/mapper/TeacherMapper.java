package com.example.eschool.mapper;

import com.example.eschool.dto.TeacherDto;
import com.example.eschool.entities.Teacher;

public class TeacherMapper {
    public static TeacherDto mapToTeacherDto (Teacher teacher){
        return new TeacherDto(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                //teacher.getPassword(),
                teacher.getSubjects().stream().map(SubjectMapper::mapToSubjectDto).toList()
        );
    }

    public static Teacher mapToTeacher (TeacherDto teacherDto){
        return new Teacher(
                teacherDto.getId(),
                teacherDto.getFirstName(),
                teacherDto.getLastName(),
                teacherDto.getEmail(),
                //teacherDto.getPassword(),
                teacherDto.getSubjects().stream().map(SubjectMapper::mapToSubject).toList()
        );
    }
}
