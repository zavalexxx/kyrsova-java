package com.example.eschool;

import com.example.eschool.dto.LessonPlanDto;
import com.example.eschool.services.LessonPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
@AutoConfigureMockMvc
public class LessonPlanTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonPlanService lessonPlanService;

    @Test
    public void testCreateLessonPlan() throws Exception {
        // Перевірка успішного додавання плану уроку
        LessonPlanDto lessonPlanDto = new LessonPlanDto();
        lessonPlanDto.setTopic("Mathematics");
        lessonPlanDto.setDescription("Algebra lesson");

        Mockito.when(lessonPlanService.createLessonPlan(any(), any(), anyLong(), any(), any()))
                .thenReturn(lessonPlanDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lesson_plans/add")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"topic\":\"Mathematics\",\"description\":\"Algebra lesson\",\"subjectId\":1,\"assignmentIds\":[1],\"materialIds\":[1]}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.topic").value("Mathematics"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Algebra lesson"));
    }


    @Test
    public void testGetAllLessonPlans() throws Exception {
        // Перевірка отримання списку всіх планів уроків
        LessonPlanDto lessonPlanDto = new LessonPlanDto();
        lessonPlanDto.setTopic("Mathematics");

        Mockito.when(lessonPlanService.getAllLessonPlans())
                .thenReturn(Collections.singletonList(lessonPlanDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lesson_plans/getAll")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].topic").value("Mathematics"));
    }


    @Test
    public void testCreateLessonPlanWithInvalidData() throws Exception {
        // Перевірка додавання плану уроку з недійсними даними
        Mockito.doThrow(IllegalArgumentException.class)
                .when(lessonPlanService)
                .createLessonPlan(any(), any(), anyLong(), any(), any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lesson_plans/add")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"topic\":\"\",\"description\":\"Algebra lesson\",\"subjectId\":1,\"assignmentIds\":[1],\"materialIds\":[1]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetLessonPlanById() throws Exception {
        // Перевірка отримання плану уроку за ID
        LessonPlanDto lessonPlanDto = new LessonPlanDto();
        lessonPlanDto.setTopic("Mathematics");

        Mockito.when(lessonPlanService.getLessonPlanById(anyLong()))
                .thenReturn(lessonPlanDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lesson_plans/get/1")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.topic").value("Mathematics"));
    }

    @Test
    public void testGetLessonPlanByNonExistentId() throws Exception {
        // Перевірка отримання плану уроку за неіснуючим ID
        Mockito.doThrow(EntityNotFoundException.class)
                .when(lessonPlanService)
                .getLessonPlanById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lesson_plans/get/999")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateLessonPlan() throws Exception {
        // Перевірка успішного оновлення плану уроку
        LessonPlanDto lessonPlanDto = new LessonPlanDto();
        lessonPlanDto.setTopic("Mathematics Updated");
        lessonPlanDto.setDescription("Updated Algebra lesson");

        Mockito.when(lessonPlanService.updateLessonPlan(anyLong(), any(), any(), anyLong(), any(), any()))
                .thenReturn(lessonPlanDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/lesson_plans/update/1")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"topic\":\"Mathematics Updated\",\"description\":\"Updated Algebra lesson\",\"subjectId\":1,\"assignmentIds\":[1],\"materialIds\":[1]}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.topic").value("Mathematics Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Algebra lesson"));
    }

    @Test
    public void testUpdateLessonPlanWithInvalidData() throws Exception {
        // Перевірка оновлення плану уроку з недійсними даними
        Mockito.doThrow(IllegalArgumentException.class)
                .when(lessonPlanService)
                .updateLessonPlan(anyLong(), any(), any(), anyLong(), any(), any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/lesson_plans/update/1")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"topic\":\"\",\"description\":\"Updated Algebra lesson\",\"subjectId\":1,\"assignmentIds\":[1],\"materialIds\":[1]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDeleteLessonPlan() throws Exception {
        // Перевірка успішного видалення плану уроку
        Mockito.doNothing().when(lessonPlanService).deleteLessonPlan(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lesson_plans/delete/1")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteNonExistentLessonPlan() throws Exception {
        // Перевірка видалення неіснуючого плану уроку
        Mockito.doThrow(EntityNotFoundException.class)
                .when(lessonPlanService)
                .deleteLessonPlan(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lesson_plans/delete/999")
                        .header("Authorization", "Bearer " + TestObjectFactory.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
