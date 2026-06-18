package com.sravanthi.githubanalyzer.dto;

import java.util.Map;

public class StatisticsDTO {

    private int totalRepositories;
    private int totalStars;
    private int totalForks;
    private String mostUsedLanguage;
    private String mostStarredRepository;
    private Map<String, Integer> languageDistribution;

    public StatisticsDTO() {
    }

    public int getTotalRepositories() {
        return totalRepositories;
    }

    public void setTotalRepositories(int totalRepositories) {
        this.totalRepositories = totalRepositories;
    }

    public int getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(int totalStars) {
        this.totalStars = totalStars;
    }

    public int getTotalForks() {
        return totalForks;
    }

    public void setTotalForks(int totalForks) {
        this.totalForks = totalForks;
    }

    public String getMostUsedLanguage() {
        return mostUsedLanguage;
    }

    public void setMostUsedLanguage(String mostUsedLanguage) {
        this.mostUsedLanguage = mostUsedLanguage;
    }

    public String getMostStarredRepository() {
        return mostStarredRepository;
    }

    public void setMostStarredRepository(String mostStarredRepository) {
        this.mostStarredRepository = mostStarredRepository;
    }

    public Map<String, Integer> getLanguageDistribution() {
        return languageDistribution;
    }

    public void setLanguageDistribution(
            Map<String, Integer> languageDistribution) {

        this.languageDistribution = languageDistribution;
    }
}