package com.example.eschool;

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
import com.example.eschool.services.implementation.LessonPlanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonPlanServiceTest {

    @Mock
    private LessonPlanRepository lessonPlanRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private LessonPlanServiceImpl lessonPlanService;

    private LessonPlan lessonPlan;
    private LessonPlanDto lessonPlanDto;
    private Subject subject;
    private Assignment assignment;
    private Material material;

    @BeforeEach
    public void setUp() {
        lessonPlan = new LessonPlan();
        lessonPlan.setLessonId(1L);
        lessonPlan.setTopic("Math");
        lessonPlan.setDescription("Math lesson plan");

        subject = new Subject();
        subject.setSubjectId(1L);
        subject.setSubjectName("Mathematics");
        lessonPlan.setSubject(subject);

        assignment = new Assignment();
        assignment.setId(1L);
        assignment.setName("Homework");
        assignment.setType("Homework");
        assignment.setMaxMark(10);

        material = new Material();
        material.setId(1L);
        material.setDescription("Textbook");
        material.setFileURL("http://example.com");

        lessonPlan.setAssignments(List.of(assignment));
        lessonPlan.setMaterials(List.of(material));

        lessonPlanDto = LessonPlanMapper.mapToLessonPlanDto(lessonPlan);
    }

    @Test
    public void testCreateLessonPlan() {
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(assignmentRepository.findById(anyLong())).thenReturn(Optional.of(assignment));
        when(materialRepository.findById(anyLong())).thenReturn(Optional.of(material));
        when(lessonPlanRepository.save(any(LessonPlan.class))).thenReturn(lessonPlan);

        LessonPlanDto result = lessonPlanService.createLessonPlan(
                "Math", "Math lesson plan", 1L, List.of(1L), List.of(1L));

        assertNotNull(result);
        assertEquals("Math", result.getTopic());
        verify(subjectRepository, times(1)).findById(1L);
        verify(assignmentRepository, times(1)).findById(1L);
        verify(materialRepository, times(1)).findById(1L);
        verify(lessonPlanRepository, times(1)).save(any(LessonPlan.class));
    }

    @Test
    public void testGetLessonPlanById() {
        when(lessonPlanRepository.findById(anyLong())).thenReturn(Optional.of(lessonPlan));

        LessonPlanDto result = lessonPlanService.getLessonPlanById(1L);

        assertNotNull(result);
        assertEquals("Math", result.getTopic());
        verify(lessonPlanRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetLessonPlanById_NotFound() {
        when(lessonPlanRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            lessonPlanService.getLessonPlanById(1L);
        });

        assertEquals("Failed to get lesson plan by ID: 1", thrown.getMessage());
        verify(lessonPlanRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllLessonPlans() {
        when(lessonPlanRepository.findAll()).thenReturn(List.of(lessonPlan));

        List<LessonPlanDto> result = lessonPlanService.getAllLessonPlans();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Math", result.get(0).getTopic());
        verify(lessonPlanRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateLessonPlan() {
        when(lessonPlanRepository.findById(anyLong())).thenReturn(Optional.of(lessonPlan));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(assignmentRepository.findById(anyLong())).thenReturn(Optional.of(assignment));
        when(materialRepository.findById(anyLong())).thenReturn(Optional.of(material));
        when(lessonPlanRepository.save(any(LessonPlan.class))).thenReturn(lessonPlan);

        LessonPlanDto result = lessonPlanService.updateLessonPlan(
                1L, "Math", "Updated Math lesson plan", 1L, List.of(1L), List.of(1L));

        assertNotNull(result);
        assertEquals("Updated Math lesson plan", result.getDescription());
        verify(lessonPlanRepository, times(1)).findById(1L);
        verify(subjectRepository, times(1)).findById(1L);
        verify(assignmentRepository, times(1)).findById(1L);
        verify(materialRepository, times(1)).findById(1L);
        verify(lessonPlanRepository, times(1)).save(any(LessonPlan.class));
    }

    @Test
    public void testDeleteLessonPlan() {
        when(lessonPlanRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(lessonPlanRepository).deleteById(anyLong());

        lessonPlanService.deleteLessonPlan(1L);

        verify(lessonPlanRepository, times(1)).existsById(1L);
        verify(lessonPlanRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteLessonPlan_NotFound() {
        when(lessonPlanRepository.existsById(anyLong())).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            lessonPlanService.deleteLessonPlan(1L);
        });

        assertEquals("Failed to delete lesson plan", thrown.getMessage());
        verify(lessonPlanRepository, times(1)).existsById(1L);
    }
}
