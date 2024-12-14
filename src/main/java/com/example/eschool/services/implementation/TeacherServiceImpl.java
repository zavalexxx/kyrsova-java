package com.example.eschool.services.implementation;

import com.example.eschool.dto.TeacherDto;
import com.example.eschool.entities.Teacher;
import com.example.eschool.mapper.TeacherMapper;
import com.example.eschool.repositories.TeacherRepository;
import com.example.eschool.services.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;
    /**
     * Creates a new teacher.
     *
     * @param teacherDto The DTO containing teacher information.
     * @return The DTO of the created teacher.
     */
    @Override
    public TeacherDto createTeacher(TeacherDto teacherDto) {
        try {
            Teacher teacher = TeacherMapper.mapToTeacher(teacherDto);
            Teacher savedTeacher = teacherRepository.save(teacher);
            return TeacherMapper.mapToTeacherDto(savedTeacher);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create teacher", e);
        }
    }

    /**
     * Retrieves a teacher by ID.
     *
     * @param teacherId The ID of the teacher to retrieve.
     * @return The DTO of the retrieved teacher.
     * @throws EntityNotFoundException if the teacher with the given ID is not found.
     */
    @Override
    public TeacherDto getTeacherById(Long teacherId) {
        try {
            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found with ID: " + teacherId));
            return TeacherMapper.mapToTeacherDto(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get teacher by ID", e);
        }
    }

    /**
     * Retrieves all teachers.
     *
     * @return A list of DTOs representing all teachers.
     */
    @Override
    public List<TeacherDto> getAllTeachers() {
        try {
            List<Teacher> teachers = teacherRepository.findAll();
            return teachers.stream().map(TeacherMapper::mapToTeacherDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all teachers", e);
        }
    }

    /**
     * Updates a teacher.
     *
     * @param teacherId     The ID of the teacher to update.
     * @param updatedTeacher The DTO containing updated teacher information.
     * @return The DTO of the updated teacher.
     * @throws EntityNotFoundException if the teacher with the given ID is not found.
     */
    @Override
    public TeacherDto updateTeacher(Long teacherId, TeacherDto updatedTeacher) {
        try {
            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found with ID: " + teacherId));

            teacher.setFirstName(updatedTeacher.getFirstName());
            teacher.setLastName(updatedTeacher.getLastName());
            teacher.setEmail(updatedTeacher.getEmail());
            //teacher.setPassword(updatedTeacher.getPassword());

            Teacher updatedTeacherObj = teacherRepository.save(teacher);

            return TeacherMapper.mapToTeacherDto(updatedTeacherObj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update teacher", e);
        }
    }

    /**
     * Deletes a teacher by ID.
     *
     * @param teacherId The ID of the teacher to delete.
     * @throws EntityNotFoundException if the teacher with the given ID is not found.
     */
    @Override
    public void deleteTeacher(Long teacherId) {
        try {
            teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

            teacherRepository.deleteById(teacherId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete teacher " + teacherId, e);
        }
    }
}
