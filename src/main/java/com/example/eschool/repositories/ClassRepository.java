package com.example.eschool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<com.example.eschool.entities.Class, Long> {
    List<com.example.eschool.entities.Class> findByTeacherId(Long teacherId);
    com.example.eschool.entities.Class findClassByClassName(String className);
}
