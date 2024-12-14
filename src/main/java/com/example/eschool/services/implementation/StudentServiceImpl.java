package com.example.eschool.services.implementation;

import com.example.eschool.dto.StudentDto;
import com.example.eschool.entities.Student;
import com.example.eschool.mapper.StudentMapper;
import com.example.eschool.repositories.StudentRepository;
import com.example.eschool.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Override
    public StudentDto createStudent (StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);
        Student savedStudent = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(savedStudent);
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow();
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToStudentDto).toList();

    }

    @Override
    public StudentDto updateStudent(Long studentId, StudentDto updatedStudent) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow();

        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());

        Student updatedStudentObj = studentRepository.save(student);

        return StudentMapper.mapToStudentDto(updatedStudentObj);
    }

    @Override
    public void deleteStudent(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student not found with ID: " + studentId));

        studentRepository.deleteById(studentId);
    }
}
