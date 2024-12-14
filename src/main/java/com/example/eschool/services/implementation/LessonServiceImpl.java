package com.example.eschool.services.implementation;

import com.example.eschool.dto.LessonDto;
import com.example.eschool.entities.*;
import com.example.eschool.mapper.*;
import com.example.eschool.repositories.*;
import com.example.eschool.services.LessonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@Service
public class LessonServiceImpl implements LessonService {
    private ClassRepository classRepository;
    private StudentRepository studentRepository;
    private LessonRepository lessonRepository;
    private TeacherRepository teacherRepository;
    private LessonPlanRepository lessonPlanRepository;

    /**
     * Retrieves list of students that should be present at a lesson.
     *
     * @param lessonId The ID of the lesson.
     * @return The List of the retrieved students' names.
     */
    @Override
    public List<String> getListOfClassStudents(Long lessonId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));

            List<Student> students = studentRepository.findStudentsByStudentClass(lesson.getClassLesson());
            return students.stream().map(student -> student.getFirstName() + " " + student.getLastName()).toList();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get students that should be present at the lesson");
        }
    }

    /**
     * Retrieves all lessons.
     *
     * @return the list of LessonDto
     */
    @Override
    public List<LessonDto> getAllLessonPlans() {
        try {
            return lessonRepository.findAll().stream()
                    .map(LessonMapper::mapToLessonDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all lessons", e);
        }
    }

    /**
     * Retrieves lesson by ID.
     *
     * @param lessonId The ID of the lesson.
     * @return The DTO of the retrieved lesson.
     */
    @Override
    public LessonDto getLessonById(Long lessonId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));
            return LessonMapper.mapToLessonDto(lesson);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get lesson by ID");
        }
    }

    /**
     * Creates a new lesson.
     *
     * @param className, teacherId, lessonPlanId for the new lesson.
     * @return The DTO of the created lesson.
     */
    @Override
    public LessonDto createLesson(String className, Long teacherId, Long lessonPlanId) {
        try {
            Lesson lesson = new Lesson();
            lesson.setLessonId(lesson.getLessonId());

            LessonPlan lp = lessonPlanRepository.findById(lessonPlanId)
                    .orElseThrow(() -> new EntityNotFoundException("Lesson plan not found with ID: " + lesson.getLessonPlan().getLessonId()));
            lesson.setLessonPlan(lp);

            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found with ID: " + teacherId));
            lesson.setTeacher(teacher);

            com.example.eschool.entities.Class cl = classRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Class not found with name: " + className));
            lesson.setClassLesson(cl);

            Lesson savedLesson = lessonRepository.save(lesson);
            return LessonMapper.mapToLessonDto(savedLesson);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create lesson", e);
        }
    }

    /**
     * Edits an existing lesson.
     *
     * @param lessonId  The ID of the lesson to edit.
     * @param className, teacherId, lessonPlanId containing the updated details of the lesson.
     * @return The DTO of the edited lesson.
     */
    @Override
    public LessonDto editLesson(Long lessonId, String className, Long teacherId, Long lessonPlanId) {
        try {
            Lesson existingLesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));

            LessonPlan lp = lessonPlanRepository.findById(lessonPlanId)
                    .orElseThrow(() -> new EntityNotFoundException("Lesson plan not found with ID: " + lessonPlanId));
            existingLesson.setLessonPlan(lp);

            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found with ID: " + teacherId));
            existingLesson.setTeacher(teacher);

            com.example.eschool.entities.Class cl = classRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Class not found with name: " + className));
            existingLesson.setClassLesson(cl);

            Lesson updatedLesson = lessonRepository.save(existingLesson);
            return LessonMapper.mapToLessonDto(updatedLesson);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update lesson", e);
        }
    }

    /**
     * Deletes a lesson by its ID.
     *
     * @param lessonId The ID of the lesson to delete.
     */
    @Override
    public void deleteLesson(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new EntityNotFoundException("Lesson not found with ID: " + lessonId);
        }
        lessonRepository.deleteById(lessonId);
    }
}
