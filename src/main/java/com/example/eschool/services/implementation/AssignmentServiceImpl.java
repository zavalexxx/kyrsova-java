package com.example.eschool.services.implementation;

import com.example.eschool.dto.AssignmentDto;
import com.example.eschool.entities.Assignment;
import com.example.eschool.mapper.AssignmentMapper;
import com.example.eschool.repositories.AssignmentRepository;
import com.example.eschool.services.AssignmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;

    /**
     * Creates a new assignment.
     *
     * @param assignmentDto The assignment DTO containing assignment details.
     * @return The DTO of the created assignment.
     */
    @Override
    public AssignmentDto createAssignment(AssignmentDto assignmentDto) {
        try {
            Assignment assignment = AssignmentMapper.mapToAssignment(assignmentDto);
            Assignment savedAssignment = assignmentRepository.save(assignment);
            return AssignmentMapper.mapToAssignmentDto(savedAssignment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create assignment", e);
        }
    }

    /**
     * Retrieves an assignment by ID.
     *
     * @param id The ID of the assignment to retrieve.
     * @return The DTO of the retrieved assignment.
     * @throws EntityNotFoundException If the assignment with the given ID is not found.
     */
    @Override
    public AssignmentDto getAssignmentById(Long id) {
        try {
            Assignment assignment = assignmentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Assignment not found with ID: " + id));
            return AssignmentMapper.mapToAssignmentDto(assignment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get assignment by ID", e);
        }
    }

    /**
     * Retrieves all assignments.
     *
     * @return The list of assignment DTOs.
     */
    @Override
    public List<AssignmentDto> getAllAssignments() {
        try {
            return assignmentRepository.findAll().stream()
                    .map(AssignmentMapper::mapToAssignmentDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all assignments", e);
        }
    }

    /**
     * Updates an existing assignment.
     *
     * @param id            The ID of the assignment to update.
     * @param assignmentDto The DTO containing updated assignment details.
     * @return The DTO of the updated assignment.
     * @throws EntityNotFoundException If the assignment with the given ID is not found.
     */
    @Override
    public AssignmentDto updateAssignment(Long id, AssignmentDto assignmentDto) {
        try {
            Assignment existingAssignment = assignmentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Assignment not found with ID: " + id));

            existingAssignment.setType(assignmentDto.getType());
            existingAssignment.setName(assignmentDto.getName());
            existingAssignment.setMaxMark(assignmentDto.getMaxMark());

            Assignment updatedAssignment = assignmentRepository.save(existingAssignment);
            return AssignmentMapper.mapToAssignmentDto(updatedAssignment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update assignment", e);
        }
    }

    /**
     * Deletes an assignment by ID.
     *
     * @param id The ID of the assignment to delete.
     * @throws EntityNotFoundException If the assignment with the given ID is not found.
     */
    @Override
    public void deleteAssignment(Long id) {
        try {
            if (!assignmentRepository.existsById(id)) {
                throw new EntityNotFoundException("Assignment not found with ID: " + id);
            }
            assignmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete assignment", e);
        }
    }
}
