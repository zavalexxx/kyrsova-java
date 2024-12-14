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
@Table(name = "classes")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "class_id")
    private Long id;

    @Column (name = "name")
    private String className;

    @OneToOne
    @JoinColumn (name = "teacher_id")
    private Teacher teacher;

    @Column (name = "year_start")
    private Integer yearStart;

    @Column (name = "year_finish")
    private Integer yearFinish;
}
