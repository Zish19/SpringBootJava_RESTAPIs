# SpringBootJava_RESTAPIs

# Project 2: Student Management REST API

A production-grade, modular RESTful API built with **Java Spring Boot**, **Spring Data JPA**, **Hibernate**, and **MySQL**. Specifically engineered for college assignments, academic lab submissions, and practical examinations requiring verified proof of implementation.

---

## 📋 Table of Contents
- [Project Overview](#project-overview)
- [Three Minimal AI Prompts Used for Creation](#three-minimal-ai-prompts-used-for-creation)
- [System Architecture](#system-architecture)
- [Six Required REST APIs](#six-required-rest-apis)
- [Tech Stack & Prerequisites](#tech-stack--prerequisites)
- [Database Setup (MySQL)](#database-setup-mysql)
- [Setup & Execution Instructions](#setup--execution-instructions)
- [Postman Testing & Collection](#postman-testing--collection)
- [Assignment Proof of Implementation Screenshots](#assignment-proof-of-implementation-screenshots)
- [Error Handling & Duplicate Prevention](#error-handling--duplicate-prevention)

---

## 🚀 Project Overview

The **Student Management REST API** provides comprehensive backend services to manage student academic records, subject test scores, calculated performance metrics (average scores), and industry-relevant technical skill sets. It adheres to strict enterprise design patterns, separation of concerns, and robust error management.

### Key Highlights
- **Layered Architecture:** Strict Entity (Model) → Repository → Service → Controller pattern.
- **Dynamic Average Score Calculation:** Automatically recalculates `averageScore` whenever scores are inserted or modified.
- **Duplicate ID & Duplicate Email Prevention:** Returns RFC-compliant `409 Conflict` if a duplicate student ID or email is detected.
- **Academic Merit List:** Native JPA query sorting students by `averageScore` in descending order.
- **Relational Integrity:** Implements `@ElementCollection` and `@CollectionTable` for scores and skills with cascade deletions.
- **Input Validation:** Enforces `@NotBlank`, `@Email`, `@NotEmpty`, and `@Positive` constraints with `400 Bad Request` feedback.

---

## 🤖 Three Minimal AI Prompts Used for Creation

To reproduce or scaffold this entire project using modern Generative AI assistants, use these three minimal, highly specific prompts:

### **Prompt 1: Entity & Database Modeling**
> *"Build a Spring Boot Student Management system using Spring Data JPA and MySQL. Create an entity `Student` with fields `id` (Long, client-assigned with duplicate prevention), `name`, `email` (unique), `department`, `scores` (List of Doubles), `averageScore` (calculated automatically), and `technicalSkills` (List of Strings) mapped via JPA `@ElementCollection`. Include full constructors, explicit getters, setters, and `StudentRepository` with custom query methods."*

### **Prompt 2: Business Logic & Exception Handling**
> *"Implement a Service layer and `@RestControllerAdvice` Global Exception Handler for the Student Management system. Enforce duplicate ID prevention (throw `DuplicateIdException` returning HTTP 409 Conflict), missing record lookup (throw `ResourceNotFoundException` returning HTTP 404), automatic average score computation upon create/update, and bean validation error handling returning HTTP 400 Bad Request."*

### **Prompt 3: REST Controller & Merit List Endpoints**
> *"Create a REST controller `StudentController` mapped to `/api/students` exposing all 6 operations: 1. Add student (POST, 201 Created), 2. Get all students (GET, 200 OK), 3. Search student by ID (GET /{id}, 200 OK), 4. Update scores and technical skills (PUT /{id}/scores-and-skills, 200 OK), 5. Delete student (DELETE /{id}, 200 OK), and 6. Display merit list sorted by average score descending (GET /merit-list, 200 OK). Provide a MySQL setup script and Postman collection."*

---

## 🏛️ System Architecture

```
Project02/
├── pom.xml                                   # Maven dependencies & build config
├── README.md                                 # Project documentation & practical guide
├── .gitignore                                # Git ignore file
├── sql/
│   └── setup.sql                             # MySQL DDL schema and initial seed data
├── postman/
│   └── Student_Management_API.postman_collection.json # 6 API requests + error test
├── screenshots/                              # Proof of implementation captures
│   ├── 01_Model_Student.png                  # Model source screenshot
│   ├── 02_Controller_StudentController.png   # Controller source screenshot
│   ├── 03_Postman_Add_Student.png            # Postman Op 1: 201 Created
│   ├── 04_Postman_Retrieve_All_Students.png  # Postman Op 2: 200 OK
│   ├── 05_Postman_Search_Student_By_ID.png   # Postman Op 3: 200 OK
│   ├── 06_Postman_Update_Scores_And_Skills.png # Postman Op 4: 200 OK
│   ├── 07_Postman_Delete_Student.png         # Postman Op 5: 200 OK
│   ├── 08_Postman_Display_Merit_List.png     # Postman Op 6: 200 OK
│   └── 09_Postman_Duplicate_ID_Validation.png# Postman Op 7: 409 Conflict
└── src/
    ├── main/
    │   ├── java/com/college/studentmanagement/
    │   │   ├── StudentManagementApplication.java
    │   │   ├── model/
    │   │   │   └── Student.java
    │   │   ├── repository/
    │   │   │   └── StudentRepository.java
    │   │   ├── service/
    │   │   │   ├── StudentService.java
    │   │   │   └── impl/StudentServiceImpl.java
    │   │   ├── controller/
    │   │   │   └── StudentController.java
    │   │   ├── dto/
    │   │   │   ├── ApiResponse.java
    │   │   │   └── UpdateScoresSkillsRequest.java
    │   │   └── exception/
    │   │       ├── DuplicateIdException.java
    │   │       ├── ResourceNotFoundException.java
    │   │       ├── ErrorResponse.java
    │   │       └── GlobalExceptionHandler.java
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/com/college/studentmanagement/
            └── StudentManagementApplicationTests.java
```

---

## ⚡ Six Required REST APIs

| # | Operation | HTTP Method | Endpoint URL | Status Code | Description |
|---|---|:---:|---|:---:|---|
| **1** | **Add a Student** | `POST` | `/api/students` | `201 Created` | Inserts new student record, auto-computes average score, checks duplicate ID/email. |
| **2** | **Retrieve All Students** | `GET` | `/api/students` | `200 OK` | Returns full list of student records stored in MySQL. |
| **3** | **Search Student by ID** | `GET` | `/api/students/{id}` | `200 OK` / `404` | Finds a specific student by primary key ID. |
| **4** | **Update Scores & Skills** | `PUT` | `/api/students/{id}/scores-and-skills` | `200 OK` | Updates subject scores and technical skills, recalculating `averageScore`. |
| **5** | **Delete a Student Record** | `DELETE` | `/api/students/{id}` | `200 OK` | Removes student and cascaded scores/skills records by ID. |
| **6** | **Display Merit List** | `GET` | `/api/students/merit-list` | `200 OK` | Displays all students sorted by `averageScore` descending. |

---

## 📦 Request & Response Payload Examples

### 1. Add Student (`POST /api/students`)
**Request Body:**
```json
{
  "id": 105,
  "name": "Vikram Malhotra",
  "email": "vikram.malhotra@university.edu",
  "department": "Artificial Intelligence",
  "scores": [88.0, 95.5, 92.5],
  "technicalSkills": ["Python", "PyTorch", "TensorFlow", "FastAPI"]
}
```
**Response (201 Created):**
```json
{
  "id": 105,
  "name": "Vikram Malhotra",
  "email": "vikram.malhotra@university.edu",
  "department": "Artificial Intelligence",
  "scores": [88.0, 95.5, 92.5],
  "averageScore": 92.0,
  "technicalSkills": ["Python", "PyTorch", "TensorFlow", "FastAPI"]
}
```

### 4. Update Student Scores and Skills (`PUT /api/students/105/scores-and-skills`)
**Request Body:**
```json
{
  "scores": [96.0, 98.0, 97.0],
  "technicalSkills": ["Python", "PyTorch", "TensorFlow", "FastAPI", "Docker", "MLOps"]
}
```
**Response (200 OK):**
```json
{
  "id": 105,
  "name": "Vikram Malhotra",
  "email": "vikram.malhotra@university.edu",
  "department": "Artificial Intelligence",
  "scores": [96.0, 98.0, 97.0],
  "averageScore": 97.0,
  "technicalSkills": ["Python", "PyTorch", "TensorFlow", "FastAPI", "Docker", "MLOps"]
}
```

### 5. Delete Student Record (`DELETE /api/students/103`)
**Response (200 OK):**
```json
{
  "success": true,
  "message": "Student record with ID 103 deleted successfully.",
  "timestamp": "2026-11-19T11:21:57"
}
```

---

## 🛠️ Database Setup (MySQL)

1. Ensure MySQL Server is running on port `3306`.
2. Connect to MySQL client:
   ```bash
   mysql -u root -p
   ```
3. Run the provided database initialization script `sql/setup.sql`:
   ```bash
   mysql -u root < sql/setup.sql
   ```
4. Verify database and initial seed data:
   ```sql
   USE student_management_db;
   SELECT * FROM students;
   SELECT * FROM student_scores;
   SELECT * FROM student_technical_skills;
   ```

---

## 🚀 Setup & Execution Instructions

### Step 1: Clone Repository
```bash
git clone https://github.com/Zish19/SpringBootJava_RESTAPIs.git
cd SpringBootJava_RESTAPIs
```

### Step 2: Configure Database Credentials
Open `src/main/resources/application.properties` and verify your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
```
*(Leave password empty if root has no password, or supply your MySQL password)*

### Step 3: Build & Package with Maven
```bash
mvn clean package -DskipTests
```

### Step 4: Run the Spring Boot Application
```bash
java -jar target/student-management-api-1.0.0.jar
```
*Or using the Spring Boot Maven plugin:*
```bash
mvn spring-boot:run
```
Tomcat will initialize on port **8080** (`http://localhost:8080`).

---

## 📬 Postman Testing & Collection

A complete, pre-configured Postman collection is included in `postman/Student_Management_API.postman_collection.json`.

1. Open Postman.
2. Click **Import** (top left).
3. Select `postman/Student_Management_API.postman_collection.json`.
4. Execute the requests in sequence:
   - `1. Add Student` (POST)
   - `2. Retrieve All Students` (GET)
   - `3. Search Student by ID` (GET)
   - `4. Update Student Scores and Technical Skills` (PUT)
   - `5. Delete Student Record` (DELETE)
   - `6. Display Merit List` (GET)
   - `7. Duplicate ID Conflict Test` (POST - returns 409 Conflict)

---

## 📸 Assignment Proof of Implementation Screenshots

All screenshots are stored in high-resolution PNG format inside the `screenshots/` directory:

| Screenshot | Description | Preview |
|---|---|:---:|
| `01_Model_Student.png` | Model source code (`Student.java`) showing all attributes, constructors, getters, setters, and technical skills. | `screenshots/01_Model_Student.png` |
| `02_Controller_StudentController.png` | Controller source code (`StudentController.java`) showing all six REST API endpoints simultaneously. | `screenshots/02_Controller_StudentController.png` |
| `03_Postman_Add_Student.png` | Postman execution: Add Student (POST) returning `201 Created`. | `screenshots/03_Postman_Add_Student.png` |
| `04_Postman_Retrieve_All_Students.png` | Postman execution: Retrieve All Students (GET) returning `200 OK`. | `screenshots/04_Postman_Retrieve_All_Students.png` |
| `05_Postman_Search_Student_By_ID.png` | Postman execution: Search Student by ID (GET) returning `200 OK`. | `screenshots/05_Postman_Search_Student_By_ID.png` |
| `06_Postman_Update_Scores_And_Skills.png` | Postman execution: Update Scores & Skills (PUT) returning `200 OK` with recalculated average score. | `screenshots/06_Postman_Update_Scores_And_Skills.png` |
| `07_Postman_Delete_Student.png` | Postman execution: Delete Student (DELETE) returning `200 OK` confirmation. | `screenshots/07_Postman_Delete_Student.png` |
| `08_Postman_Display_Merit_List.png` | Postman execution: Merit List (GET) sorted by `averageScore` descending. | `screenshots/08_Postman_Display_Merit_List.png` |
| `09_Postman_Duplicate_ID_Validation.png` | Postman execution: Duplicate ID prevention returning `409 Conflict`. | `screenshots/09_Postman_Duplicate_ID_Validation.png` |

---

## 🛡️ Error Handling & Duplicate Prevention

The API implements centralized, robust error handling with `@RestControllerAdvice`:
- **409 Conflict:** Thrown when attempting to register an existing student ID or email.
- **404 Not Found:** Thrown when querying or deleting a non-existent student record ID.
- **400 Bad Request:** Thrown on missing required fields, negative IDs, or malformed JSON payload.
- **500 Internal Server Error:** Catches unexpected exceptions with detailed error messages.
