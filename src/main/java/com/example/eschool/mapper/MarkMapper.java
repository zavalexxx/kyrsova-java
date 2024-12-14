package com.example.eschool.mapper;

import com.example.eschool.dto.MarkDto;
import com.example.eschool.dto.responses.MarkResponseDto;
import com.example.eschool.entities.Mark;

public class MarkMapper {
    public static MarkDto mapToMarkDto (Mark mark) {
        return new MarkDto(
                mark.getId(),
                StudentMapper.mapToStudentDto(mark.getStudent()),
                LessonMapper.mapToLessonDto(mark.getLesson()),
                mark.getDate(),
                mark.getMark()
        );
    }

    public static Mark mapToMark (MarkDto markDto) {
        return new Mark(
                markDto.getId(),
                StudentMapper.mapToStudent(markDto.getStudentDto()),
                LessonMapper.mapToLesson(markDto.getLessonDto()),
                markDto.getDate(),
                markDto.getMark()
        );
    }

    public static MarkResponseDto convertToResponseDto(MarkDto markDto) {
        MarkResponseDto responseDto = new MarkResponseDto();
        responseDto.setStudentName(markDto.getStudentDto().getFirstName() + " " + markDto.getStudentDto().getLastName());
        responseDto.setDate(markDto.getDate());
        responseDto.setSubjectName(markDto.getLessonDto().getLessonPlan().getSubject().getSubjectName());
        responseDto.setMark(markDto.getMark());
        return responseDto;
    }
}
