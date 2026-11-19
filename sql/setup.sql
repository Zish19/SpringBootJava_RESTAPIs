-- ==============================================================================
-- Project 2: Student Management REST API - Database Setup Script
-- Database: MySQL 8.x
-- Description: DDL and sample DML for Student Management System
-- ==============================================================================

-- 1. Create Database if not exists
CREATE DATABASE IF NOT EXISTS student_management_db;
USE student_management_db;

-- 2. Drop existing tables if they exist (clean setup)
DROP TABLE IF EXISTS student_technical_skills;
DROP TABLE IF EXISTS student_scores;
DROP TABLE IF EXISTS students;

-- 3. Create Main Students Table
CREATE TABLE students (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    department VARCHAR(100) NOT NULL,
    average_score DOUBLE DEFAULT 0.0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Create Student Scores Table (One-to-Many via ElementCollection)
CREATE TABLE student_scores (
    student_id BIGINT NOT NULL,
    score DOUBLE NOT NULL,
    CONSTRAINT fk_scores_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Create Technical Skills Table (One-to-Many via ElementCollection)
CREATE TABLE student_technical_skills (
    student_id BIGINT NOT NULL,
    skill VARCHAR(100) NOT NULL,
    CONSTRAINT fk_skills_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6. Insert Initial Sample Records
-- Student 101: Aarav Sharma (CS)
INSERT INTO students (id, name, email, department, average_score)
VALUES (101, 'Aarav Sharma', 'aarav.sharma@university.edu', 'Computer Science', 89.0);

INSERT INTO student_scores (student_id, score) VALUES 
(101, 85.0), (101, 92.0), (101, 90.0);

INSERT INTO student_technical_skills (student_id, skill) VALUES 
(101, 'Java'), (101, 'Spring Boot'), (101, 'MySQL'), (101, 'Git');

-- Student 102: Priya Patel (IT)
INSERT INTO students (id, name, email, department, average_score)
VALUES (102, 'Priya Patel', 'priya.patel@university.edu', 'Information Technology', 94.67);

INSERT INTO student_scores (student_id, score) VALUES 
(102, 95.0), (102, 98.0), (102, 91.0);

INSERT INTO student_technical_skills (student_id, skill) VALUES 
(102, 'Python'), (102, 'Django'), (102, 'PostgreSQL'), (102, 'Docker');

-- Student 103: Rohan Verma (ECE)
INSERT INTO students (id, name, email, department, average_score)
VALUES (103, 'Rohan Verma', 'rohan.verma@university.edu', 'Electronics & Communication', 78.33);

INSERT INTO student_scores (student_id, score) VALUES 
(103, 75.0), (103, 82.0), (103, 78.0);

INSERT INTO student_technical_skills (student_id, skill) VALUES 
(103, 'C++'), (103, 'Embedded Systems'), (103, 'IoT');

-- Student 104: Sneha Kulkarni (CS)
INSERT INTO students (id, name, email, department, average_score)
VALUES (104, 'Sneha Kulkarni', 'sneha.kulkarni@university.edu', 'Computer Science', 91.33);

INSERT INTO student_scores (student_id, score) VALUES 
(104, 90.0), (104, 94.0), (104, 90.0);

INSERT INTO student_technical_skills (student_id, skill) VALUES 
(104, 'Java'), (104, 'React'), (104, 'AWS'), (104, 'Kubernetes');

-- 7. Verification Queries
SELECT * FROM students;
SELECT * FROM student_scores;
SELECT * FROM student_technical_skills;

-- Merit List Verification Query (Ordered by Average Score Descending)
SELECT s.id, s.name, s.department, s.average_score
FROM students s
ORDER BY s.average_score DESC;
