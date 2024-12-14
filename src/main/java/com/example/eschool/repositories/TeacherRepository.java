package com.example.eschool.repositories;

import com.example.eschool.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findTeacherByLastName(String lastName);
    Teacher findTeacherById(Long id);
}

