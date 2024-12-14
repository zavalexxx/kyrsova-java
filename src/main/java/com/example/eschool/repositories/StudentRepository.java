package com.example.eschool.repositories;

import com.example.eschool.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findStudentsByStudentClass(com.example.eschool.entities.Class studentClass);

}
