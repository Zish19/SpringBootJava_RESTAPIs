package com.college.studentmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

/**
 * Student Entity representing student records in the MySQL database.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @NotNull(message = "Student ID cannot be null")
    @Positive(message = "Student ID must be a positive number")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotBlank(message = "Student name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Student email is mandatory")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Department is mandatory")
    @Column(name = "department", nullable = false)
    private String department;

    @NotEmpty(message = "Student scores list cannot be empty")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_scores", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "score")
    private List<Double> scores = new ArrayList<>();

    @Column(name = "average_score")
    private Double averageScore;

    @NotEmpty(message = "Technical skills list cannot be empty")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_technical_skills", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "skill")
    private List<String> technicalSkills = new ArrayList<>();

    // -------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------

    public Student() {
    }

    public Student(Long id, String name, String email, String department, 
                   List<Double> scores, List<String> technicalSkills) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.scores = scores != null ? scores : new ArrayList<>();
        this.technicalSkills = technicalSkills != null ? technicalSkills : new ArrayList<>();
        calculateAverageScore();
    }

    // -------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------

    public void calculateAverageScore() {
        if (this.scores != null && !this.scores.isEmpty()) {
            double sum = 0.0;
            for (Double s : this.scores) {
                sum += (s != null ? s : 0.0);
            }
            this.averageScore = Math.round((sum / this.scores.size()) * 100.0) / 100.0;
        } else {
            this.averageScore = 0.0;
        }
    }

    // -------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Double> getScores() {
        return scores;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores != null ? scores : new ArrayList<>();
        calculateAverageScore();
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills != null ? technicalSkills : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", scores=" + scores +
                ", averageScore=" + averageScore +
                ", technicalSkills=" + technicalSkills +
                '}';
    }
}
