package com.example.eschool.repositories;

import com.example.eschool.entities.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findAllByStudentId(Long id);
}
