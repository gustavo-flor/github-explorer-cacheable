package com.github.gustavoflor.githubexplorer.dto;

import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record User(String username, String bio, String company, Set<Repo> repos, Set<Follower> followers) {
    public static User of(final GithubUser githubUser,
                          final Set<GithubRepo> githubRepos,
                          final Set<GithubFollower> githubFollowers) {
        return User.builder()
                .username(githubUser.login())
                .bio(githubUser.bio())
                .company(githubUser.company())
                .repos(githubRepos.stream().map(Repo::of).collect(Collectors.toSet()))
                .followers(githubFollowers.stream().map(Follower::of).collect(Collectors.toSet()))
                .build();
    }
}
