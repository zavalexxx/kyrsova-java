package com.example.eschool.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lesson")

public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @OneToOne
    @JoinColumn(name = "class_id")
    private com.example.eschool.entities.Class classLesson;

    @OneToOne
    @JoinColumn(name = "lesson_plan_id")
    private LessonPlan lessonPlan;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
