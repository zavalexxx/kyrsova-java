package com.example.eschool.repositories;

import com.example.eschool.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAssignmentById(Long id);
}
