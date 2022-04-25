package com.github.gustavoflor.githubexplorer.controller;

import com.github.gustavoflor.githubexplorer.dto.User;
import com.github.gustavoflor.githubexplorer.exception.NotFoundException;
import com.github.gustavoflor.githubexplorer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public User findByUsername(@PathVariable final String username) {
        return userService.findByUsername(username).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUsername(@PathVariable final String username) {
        userService.deleteByUsername(username);
    }
}
