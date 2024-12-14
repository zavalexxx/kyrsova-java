package com.example.eschool;

import com.example.eschool.dto.*;

public class TestObjectFactory {
    private static final String access_token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IllPVlEzamFwWGJiT1hZLXVKbC1EYyJ9.eyJpc3MiOiJodHRwczovL2Rldi1sbXVoYm5rdTUwNDc0eXpsLmV1LmF1dGgwLmNvbS8iLCJzdWIiOiJsb3FsaTJ2VEVNOHh1ekN6c3lYQ3V4NlM4MGxSRWpaS0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9hcGkiLCJpYXQiOjE3MTc0MTA4NTcsImV4cCI6MTcxNzQ5NzI1Nywic2NvcGUiOiJyZWFkOmNsYXNzZXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMiLCJhenAiOiJsb3FsaTJ2VEVNOHh1ekN6c3lYQ3V4NlM4MGxSRWpaSyIsInBlcm1pc3Npb25zIjpbInJlYWQ6Y2xhc3NlcyJdfQ.Kwu6t4t8UCxMfdkC5X6K78b206qF0iBY2uxXbLpViDdgZrk4JriYXLBtB4D6_nPKYh9uUUqvy4yEqwHmDThTd03BWcmT_mgLW0sZLq3Go9y8apZASHKizNgyCktWlc6J-CE0kviVArClzcVtC5pTKWAzzILueDDb4xMQKxYlc3nuc3CewEgWF5fuzSHEM5STonBXu-g4upLpyEABvxQq6pXdLBlyIHTjOzSkGzih9AIAq_i_TgcUJ322VX_IlQdOpsBfxvhnbMT47Xy2qRsqqtYY9CrBat52V7IzQG34wNX5yiY2vIg3vAkf6Zv1Gg4288L0yVOZjfUgKPzGK7F3qg";
    public static String getAccessToken() {
        return access_token;
    }

    public static StudentDto createSampleStudent(Long id, String firstName, String lastName, ClassDto classDto) {
        StudentDto student = new StudentDto();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setStudentClass(classDto);
        return student;
    }

    public static ClassDto createSampleClass(Long id) {
        ClassDto classDto = new ClassDto();
        classDto.setId(id);
        return classDto;
    }

    public static TeacherDto createSampleTeacher(Long id) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(id);
        return teacherDto;
    }

    public static LessonPlanDto createSampleLessonPlanDto(Long id) {
        LessonPlanDto lpDto = new LessonPlanDto();
        lpDto.setLessonId(id);
        return lpDto;
    }

    public static SubjectDto createSampleSubject(Long id) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectId(id);
        return subjectDto;
    }

    public static LessonDto createSampleLesson(Long id, TeacherDto teacherDto, ClassDto _class, LessonPlanDto lp) {
        LessonDto lesson = new LessonDto();
        lesson.setId(1L);
        lesson.setTeacher(teacherDto);
        lesson.setClassLesson(_class);
        lesson.setLessonPlan(lp);
        return lesson;
    }

    public static MarkDto createSampleMark(Long id, Integer markValue, LessonDto lessonDto, StudentDto studentDto) {
        MarkDto mark = new MarkDto();
        mark.setId(id);
        mark.setMark(markValue);
        mark.setLessonDto(lessonDto);
        mark.setStudentDto(studentDto);
        return mark;
    }
}
