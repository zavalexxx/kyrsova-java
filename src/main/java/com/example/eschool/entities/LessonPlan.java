package com.example.eschool.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lesson_plan")
public class LessonPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_plan_id")
    private Long lessonId;

    @Column(name = "topic")
    private String topic;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToMany
    @JoinTable(
            name = "lesson_assignment",
            joinColumns = @JoinColumn(name = "lesson_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "assignment_id")
    )
    private List<Assignment> assignments;

    @ManyToMany
    @JoinTable(
            name = "lesson_plan_materials",
            joinColumns = @JoinColumn(name = "lesson_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Material> materials;

}
