package com.college.studentmanagement.repository;

import com.college.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for Student entity.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Check if a student already exists with the given email address.
     */
    boolean existsByEmail(String email);

    /**
     * Retrieve all students sorted by averageScore in descending order (Merit List).
     */
    List<Student> findAllByOrderByAverageScoreDesc();

    /**
     * Query to find the highest existing student ID.
     */
    @Query("SELECT MAX(s.id) FROM Student s")
    Long findMaxId();
}
