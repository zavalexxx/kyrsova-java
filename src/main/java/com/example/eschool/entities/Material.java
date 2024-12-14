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
@Table(name = "materials")
public class Material {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "file_url")
    private String fileURL;

    @ManyToMany(mappedBy = "materials")
    private List<LessonPlan> lessonPlans;

    public Material(Long id, String name, String description, String fileURL) {
        this.Id = id;
        this.name = name;
        this.description = description;
        this.fileURL = fileURL;
    }
}
