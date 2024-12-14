package com.example.eschool.repositories;

import com.example.eschool.entities.Attendance;
import com.example.eschool.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findAllByStudentId(Long id);
    List<Attendance> findByLesson(Lesson lesson);
}
