package com.github.gustavoflor.githubexplorer.service;

import com.github.gustavoflor.githubexplorer.dto.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(final String username);

    void deleteByUsername(final String username);
}
