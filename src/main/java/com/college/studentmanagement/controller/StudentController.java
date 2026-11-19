package com.college.studentmanagement.controller;

import com.college.studentmanagement.dto.ApiResponse;
import com.college.studentmanagement.dto.UpdateScoresSkillsRequest;
import com.college.studentmanagement.model.Student;
import com.college.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing endpoints for Student Management operations.
 * Base URL: /api/students
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 1. Add a student
    @PostMapping
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.addStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    // 2. Retrieve all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // 3. Search a student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    // 4. Update student scores and technical skills
    @PutMapping(value = {"/{id}", "/{id}/scores-and-skills"})
    public ResponseEntity<Student> updateStudentScoresAndSkills(
            @PathVariable Long id,
            @Valid @RequestBody UpdateScoresSkillsRequest request) {
        Student updatedStudent = studentService.updateScoresAndSkills(id, request);
        return ResponseEntity.ok(updatedStudent);
    }

    // 5. Delete a student record
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        ApiResponse response = new ApiResponse(true, "Student record with ID " + id + " deleted successfully.");
        return ResponseEntity.ok(response);
    }

    // 6. Display the merit list sorted by average score in descending order
    @GetMapping("/merit-list")
    public ResponseEntity<List<Student>> getMeritList() {
        List<Student> meritList = studentService.getMeritList();
        return ResponseEntity.ok(meritList);
    }
}
