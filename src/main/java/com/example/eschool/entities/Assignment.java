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
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long Id;

    @Column(name = "assignment_type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "max_mark")
    private Integer maxMark;

    @ManyToMany(mappedBy = "assignments")
    private List<LessonPlan> lessonPlans;

    public Assignment(Long id, String type, String name, Integer maxMark) {
        this.Id = id;
        this.type = type;
        this.name = name;
        this.maxMark = maxMark;
    }
}
