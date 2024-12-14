package com.example.eschool.mapper;


import com.example.eschool.dto.AssignmentDto;
import com.example.eschool.entities.Assignment;

public class AssignmentMapper {
    public static AssignmentDto mapToAssignmentDto (Assignment assignment) {
        if (assignment == null) {
            return null;
        }
        return new AssignmentDto(
                assignment.getId(),
                assignment.getName(),
                assignment.getType(),
                assignment.getMaxMark()
        );
    }

    public static Assignment mapToAssignment (AssignmentDto assignmentDto) {
        if (assignmentDto == null) {
            return null;
        }
        return new Assignment(
                assignmentDto.getId(),
                assignmentDto.getName(),
                assignmentDto.getType(),
                assignmentDto.getMaxMark()
        );
    }
}
