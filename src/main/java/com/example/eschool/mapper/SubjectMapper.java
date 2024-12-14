package com.example.eschool.mapper;

import com.example.eschool.dto.SubjectDto;
import com.example.eschool.entities.Subject;

public class SubjectMapper {
    public static SubjectDto mapToSubjectDto(Subject subject){
        return new SubjectDto(
                subject.getSubjectId(),
                subject.getSubjectName()
        );
    }

    public static Subject mapToSubject(SubjectDto subjectDto){
        return new Subject(
                subjectDto.getSubjectId(),
                subjectDto.getSubjectName()
        );
    }
}
