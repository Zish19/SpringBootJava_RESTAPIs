package com.college.studentmanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Request payload for updating student scores and technical skills.
 */
public class UpdateScoresSkillsRequest {

    @NotEmpty(message = "Scores list cannot be empty")
    private List<Double> scores;

    @NotEmpty(message = "Technical skills list cannot be empty")
    private List<String> technicalSkills;

    public UpdateScoresSkillsRequest() {
    }

    public UpdateScoresSkillsRequest(List<Double> scores, List<String> technicalSkills) {
        this.scores = scores;
        this.technicalSkills = technicalSkills;
    }

    public List<Double> getScores() {
        return scores;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }
}
