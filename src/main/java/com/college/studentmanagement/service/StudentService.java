package com.college.studentmanagement.service;

import com.college.studentmanagement.dto.UpdateScoresSkillsRequest;
import com.college.studentmanagement.model.Student;

import java.util.List;

/**
 * Service interface defining business logic for Student Management operations.
 */
public interface StudentService {

    /**
     * Add a new student record to the system with duplicate prevention.
     */
    Student addStudent(Student student);

    /**
     * Retrieve all students stored in the database.
     */
    List<Student> getAllStudents();

    /**
     * Search and retrieve a student by their unique ID.
     */
    Student getStudentById(Long id);

    /**
     * Update student scores and technical skills, recalculating average score.
     */
    Student updateScoresAndSkills(Long id, UpdateScoresSkillsRequest request);

    /**
     * Delete a student record by ID.
     */
    void deleteStudent(Long id);

    /**
     * Retrieve the merit list of students sorted by average score in descending order.
     */
    List<Student> getMeritList();
}
