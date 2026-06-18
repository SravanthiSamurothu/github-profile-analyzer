package com.sravanthi.githubanalyzer.controller;

import com.sravanthi.githubanalyzer.dto.RepositoryDTO;
import com.sravanthi.githubanalyzer.dto.StatisticsDTO;
import com.sravanthi.githubanalyzer.dto.UserProfileDTO;
import com.sravanthi.githubanalyzer.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
@CrossOrigin("*")
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @GetMapping("/profile/{username}")
    public UserProfileDTO getProfile(
            @PathVariable String username) {

        return gitHubService.getProfile(username);
    }

    @GetMapping("/repositories/{username}")
    public List<RepositoryDTO> getRepositories(
            @PathVariable String username) {

        return gitHubService.getRepositories(username);
    }

    @GetMapping("/stats/{username}")
    public StatisticsDTO getStatistics(
            @PathVariable String username) {

        return gitHubService.getStatistics(username);
    }
}