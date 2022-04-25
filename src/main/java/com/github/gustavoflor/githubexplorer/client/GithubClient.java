package com.github.gustavoflor.githubexplorer.client;

import com.github.gustavoflor.githubexplorer.dto.GithubFollower;
import com.github.gustavoflor.githubexplorer.dto.GithubRepo;
import com.github.gustavoflor.githubexplorer.dto.GithubUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "github-client", url = "https://api.github.com")
public interface GithubClient {
    @GetMapping("/users/{username}")
    GithubUser findUserByUsername(@PathVariable final String username);

    @GetMapping("/users/{username}/repos")
    Set<GithubRepo> findReposByUsername(@PathVariable final String username);

    @GetMapping("/users/{username}/followers")
    Set<GithubFollower> findFollowersByUsername(@PathVariable final String username);
}
