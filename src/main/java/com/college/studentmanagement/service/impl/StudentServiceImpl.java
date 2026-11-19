package com.college.studentmanagement.service.impl;

import com.college.studentmanagement.dto.UpdateScoresSkillsRequest;
import com.college.studentmanagement.exception.DuplicateIdException;
import com.college.studentmanagement.exception.ResourceNotFoundException;
import com.college.studentmanagement.model.Student;
import com.college.studentmanagement.repository.StudentRepository;
import com.college.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of StudentService interface.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        // Duplicate ID prevention check
        if (student.getId() != null && studentRepository.existsById(student.getId())) {
            throw new DuplicateIdException("Student with ID " + student.getId() + " already exists in the system.");
        }

        // Duplicate Email prevention check
        if (student.getEmail() != null && studentRepository.existsByEmail(student.getEmail())) {
            throw new DuplicateIdException("Student with email '" + student.getEmail() + "' already exists in the system.");
        }

        // If ID is not specified, auto-assign the next sequential ID
        if (student.getId() == null) {
            Long maxId = studentRepository.findMaxId();
            student.setId(maxId != null ? maxId + 1 : 101L);
        }

        // Ensure average score is computed from provided scores
        student.calculateAverageScore();

        return studentRepository.save(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student record not found with ID: " + id));
    }

    @Override
    public Student updateScoresAndSkills(Long id, UpdateScoresSkillsRequest request) {
        Student student = getStudentById(id);

        student.setScores(request.getScores());
        student.setTechnicalSkills(request.getTechnicalSkills());
        student.calculateAverageScore();

        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getMeritList() {
        return studentRepository.findAllByOrderByAverageScoreDesc();
    }
}
