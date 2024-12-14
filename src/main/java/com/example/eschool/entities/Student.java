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
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "student_id")
    private Long id;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class studentClass;

    @Column (name = "parent_name")
    private String parentName;

    @Column (name = "parent_contact_phone")
    private String parentContactPhone;

    @Column (name = "parent_contact_email")
    private String parentContactEmail;
}
