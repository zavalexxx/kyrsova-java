package com.example.eschool.services.implementation;

import com.example.eschool.dto.LessonPlanDto;
import com.example.eschool.entities.Assignment;
import com.example.eschool.entities.LessonPlan;
import com.example.eschool.entities.Material;
import com.example.eschool.entities.Subject;
import com.example.eschool.mapper.LessonPlanMapper;
import com.example.eschool.repositories.AssignmentRepository;
import com.example.eschool.repositories.LessonPlanRepository;
import com.example.eschool.repositories.MaterialRepository;
import com.example.eschool.repositories.SubjectRepository;
import com.example.eschool.services.LessonPlanService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LessonPlanServiceImpl implements LessonPlanService {
    private LessonPlanRepository lessonPlanRepository;
    private AssignmentRepository assignmentRepository;
    private MaterialRepository materialRepository;
    private SubjectRepository subjectRepository;

    /**
     * Creates a new LessonPlan.
     *
     * @param topic        the topic of the lesson plan
     * @param description  the description of the lesson plan
     * @param subjectId    the ID of the subject
     * @param assignmentIds the list of assignment IDs
     * @param materialIds   the list of material IDs
     * @return the created LessonPlanDto
     */
    @Override
    public LessonPlanDto createLessonPlan(String topic, String description, Long subjectId, List<Long> assignmentIds, List<Long> materialIds) {
        try {
            LessonPlan lessonPlan = new LessonPlan();
            lessonPlan.setLessonId(lessonPlan.getLessonId());
            lessonPlan.setTopic(topic);
            lessonPlan.setDescription(description);

            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + subjectId));
            lessonPlan.setSubject(subject);

            List<Assignment> assignments = assignmentIds.stream()
                    .map(id -> assignmentRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Assignment not found with ID: " + id)))
                    .collect(Collectors.toList());
            lessonPlan.setAssignments(assignments);

            List<Material> materials = materialIds.stream()
                    .map(id -> materialRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Material not found with ID: " + id)))
                    .collect(Collectors.toList());
            lessonPlan.setMaterials(materials);

            LessonPlan savedLessonPlan = lessonPlanRepository.save(lessonPlan);
            return LessonPlanMapper.mapToLessonPlanDto(savedLessonPlan);
        }  catch (Exception e) {
            throw new RuntimeException("Failed to create lesson plan", e);
        }
    }

    /**
     * Gets a LessonPlan by its ID.
     *
     * @param id the ID of the lesson plan
     * @return the LessonPlanDto
     */
    @Override
    public LessonPlanDto getLessonPlanById(Long id) {
        try {
            LessonPlan lessonPlan = lessonPlanRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("LessonPlan not found with ID: " + id));
            return LessonPlanMapper.mapToLessonPlanDto(lessonPlan);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get lesson plan by ID: " + id, e);
        }
    }

    /**
     * Gets all LessonPlans.
     *
     * @return the list of LessonPlanDto
     */
    @Override
    public List<LessonPlanDto> getAllLessonPlans() {
        try {
            return lessonPlanRepository.findAll().stream()
                    .map(LessonPlanMapper::mapToLessonPlanDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all lesson plans", e);
        }
    }

    /**
     * Updates an existing LessonPlan.
     *
     * @param lessonPlanId  the ID of the lesson plan to update
     * @param topic         the new topic of the lesson plan
     * @param description   the new description of the lesson plan
     * @param subjectId     the ID of the new subject
     * @param assignmentIds the list of new assignment IDs
     * @param materialIds   the list of new material IDs
     * @return the updated LessonPlanDto
     */
    @Override
    public LessonPlanDto updateLessonPlan(Long lessonPlanId, String topic, String description, Long subjectId, List<Long> assignmentIds, List<Long> materialIds) {
        try {
            LessonPlan existingLessonPlan = lessonPlanRepository.findById(lessonPlanId)
                    .orElseThrow(() -> new EntityNotFoundException("LessonPlan not found with ID: " + lessonPlanId));

            existingLessonPlan.setTopic(topic);
            existingLessonPlan.setDescription(description);

            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + subjectId));

            List<Assignment> assignments = assignmentIds.stream()
                    .map(id -> assignmentRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Assignment not found with ID: " + id)))
                    .collect(Collectors.toList());

            List<Material> materials = materialIds.stream()
                    .map(id -> materialRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Material not found with ID: " + id)))
                    .collect(Collectors.toList());

            existingLessonPlan.setSubject(subject);
            existingLessonPlan.setAssignments(assignments);
            existingLessonPlan.setMaterials(materials);

            LessonPlan updatedLessonPlan = lessonPlanRepository.save(existingLessonPlan);
            return LessonPlanMapper.mapToLessonPlanDto(updatedLessonPlan);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update lesson plan", e);
        }
    }

    /**
     * Deletes a LessonPlan by its ID.
     *
     * @param id the ID of the lesson plan to delete
     */
    @Override
    public void deleteLessonPlan(Long id) {
        try {
            if (!lessonPlanRepository.existsById(id)) {
                throw new EntityNotFoundException("LessonPlan not found with ID: " + id);
            }
            lessonPlanRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete lesson plan", e);
        }
    }
}
