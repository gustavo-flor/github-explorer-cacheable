package com.github.gustavoflor.githubexplorer.dto;

public record Follower(String username) {
    public static Follower of(final GithubFollower githubFollower) {
        return new Follower(githubFollower.login());
    }
}
