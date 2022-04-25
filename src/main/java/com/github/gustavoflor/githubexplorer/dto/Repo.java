package com.github.gustavoflor.githubexplorer.dto;

public record Repo(String name, String description) {
    public static Repo of(final GithubRepo githubRepo) {
        return new Repo(githubRepo.name(), githubRepo.description());
    }
}
