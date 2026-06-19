package com.sravanthi.githubanalyzer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sravanthi.githubanalyzer.dto.RepositoryDTO;
import com.sravanthi.githubanalyzer.dto.StatisticsDTO;
import com.sravanthi.githubanalyzer.dto.UserProfileDTO;
import com.sravanthi.githubanalyzer.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GitHubService {

@Autowired
private RestTemplate restTemplate;

private static final String BASE_URL =
        "https://api.github.com/users/";

public UserProfileDTO getProfile(String username) {

    try {

        String url = BASE_URL + username;

        return restTemplate.getForObject(
                url,
                UserProfileDTO.class
        );

    } catch (Exception e) {

        throw new UserNotFoundException(
                "GitHub user not found: " + username
        );
    }
}

public List<RepositoryDTO> getRepositories(
        String username) {

    String url =
            BASE_URL + username + "/repos";

    JsonNode repos =
            restTemplate.getForObject(
                    url,
                    JsonNode.class
            );

    List<RepositoryDTO> repositoryList =
            new ArrayList<>();

    if (repos == null) {
        return repositoryList;
    }

    for (JsonNode repo : repos) {

        RepositoryDTO dto =
                new RepositoryDTO();

        dto.setName(
                repo.get("name").asText()
        );

        JsonNode descriptionNode =
                repo.get("description");

        dto.setDescription(
                descriptionNode == null
                        || descriptionNode.isNull()
                        ? "No Description Available"
                        : descriptionNode.asText()
        );

        JsonNode languageNode =
                repo.get("language");

        String language = "Unknown";

        if (languageNode != null
                && !languageNode.isNull()) {

            language =
                    languageNode.asText();
        }

        dto.setLanguage(language);

        dto.setStars(
                repo.get("stargazers_count")
                        .asInt()
        );

        dto.setForks(
                repo.get("forks_count")
                        .asInt()
        );

        dto.setOpenIssues(
                repo.get("open_issues_count")
                        .asInt()
        );

        dto.setUpdatedAt(
                repo.get("updated_at")
                        .asText()
        );

        repositoryList.add(dto);
    }

    return repositoryList;
}

public StatisticsDTO getStatistics(
        String username) {

    List<RepositoryDTO> repos =
            getRepositories(username);

    StatisticsDTO stats =
            new StatisticsDTO();

    stats.setTotalRepositories(
            repos.size()
    );

    int totalStars = 0;
    int totalForks = 0;

    Map<String, Integer> languageMap =
            new HashMap<>();

    String mostStarredRepo = "";
    int highestStars = 0;

    for (RepositoryDTO repo : repos) {

        totalStars += repo.getStars();
        totalForks += repo.getForks();

        String language =
                repo.getLanguage();

        if (!"Unknown".equals(language)
                && !"null".equals(language)) {

            languageMap.put(
                    language,
                    languageMap.getOrDefault(
                            language,
                            0
                    ) + 1
            );
        }

        if (repo.getStars() > highestStars) {

            highestStars =
                    repo.getStars();

            mostStarredRepo =
                    repo.getName();
        }
    }

    stats.setTotalStars(totalStars);

    stats.setTotalForks(totalForks);

    stats.setMostStarredRepository(
            mostStarredRepo
    );

    stats.setLanguageDistribution(
            languageMap
    );

    if (!languageMap.isEmpty()) {

        String mostUsedLanguage =
                Collections.max(
                        languageMap.entrySet(),
                        Map.Entry.comparingByValue()
                ).getKey();

        stats.setMostUsedLanguage(
                mostUsedLanguage
        );
    }

    return stats;
}

}
