package com.example.eschool.services;

import com.example.eschool.dto.AssignmentDto;

import java.util.List;

public interface AssignmentService {
    AssignmentDto createAssignment(AssignmentDto assignmentDto);
    AssignmentDto getAssignmentById(Long id);
    List<AssignmentDto> getAllAssignments();
    AssignmentDto updateAssignment(Long id, AssignmentDto assignmentDto);
    void deleteAssignment(Long id);
}
