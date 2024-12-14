package com.example.eschool.services.implementation;

import com.example.eschool.dto.MarkDto;
import com.example.eschool.entities.Lesson;
import com.example.eschool.entities.Mark;
import com.example.eschool.entities.Student;
import com.example.eschool.mapper.MarkMapper;
import com.example.eschool.repositories.LessonRepository;
import com.example.eschool.repositories.MarkRepository;
import com.example.eschool.repositories.StudentRepository;
import com.example.eschool.services.MarkService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class MarkServiceImpl implements MarkService {

    private StudentRepository studentRepository;
    private LessonRepository lessonRepository;
    private MarkRepository markRepository;

    /**
     * Retrieves a Mark by its ID.
     *
     * @param markId the ID of the Mark entity
     * @return the MarkDto corresponding to the given ID
     */
    @Override
    public MarkDto getMarkById(Long markId) {
        try {
            Mark mark = markRepository.findById(markId)
                    .orElseThrow(() -> new EntityNotFoundException("Mark not found with ID: " + markId));
            return MarkMapper.mapToMarkDto(mark);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get mark by ID");
        }
    }

    /**
     * Retrieves Marks by Student ID.
     *
     * @param studentId the ID of the Student entity
     * @return the list of MarkDto objects associated with the given Student ID
     */
    @Override
    public List<MarkDto> getMarksByStudentId(Long studentId) {

            return markRepository.findAllByStudentId(studentId).stream()
                    .map(MarkMapper::mapToMarkDto).toList();

    }

    /**
     * Marks a Student for a Lesson with a given Mark value.
     *
     * @param studentId  the ID of the Student entity
     * @param lessonId   the ID of the Lesson entity
     * @param markValue  the value of the Mark to be assigned
     */
    @Override
    public void markStudent(Long studentId, Long lessonId, Integer markValue) {
        try {
            if (markValue > 12) {
                throw new IllegalArgumentException("Mark value cannot be more than 12 or less than 1");
            } else if (markValue < 1) {
                throw new IllegalArgumentException("Mark value cannot be more less than 1");
            }

            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));

            Mark mark = new Mark();
            mark.setStudent(student);
            mark.setLesson(lesson);
            mark.setMark(markValue);
            mark.setDate(LocalDate.now());

            markRepository.save(mark);
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save new mark");
        }
    }

    /**
     * Deletes a Mark by its ID.
     *
     * @param markId the ID of the Mark entity to be deleted
     */
    @Override
    public void deleteMark(Long markId) {
        try {
            markRepository.deleteById(markId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete mark");
        }
    }
}
