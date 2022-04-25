package com.github.gustavoflor.githubexplorer.service;

import com.github.gustavoflor.githubexplorer.client.GithubClient;
import com.github.gustavoflor.githubexplorer.dto.GithubFollower;
import com.github.gustavoflor.githubexplorer.dto.GithubRepo;
import com.github.gustavoflor.githubexplorer.dto.GithubUser;
import com.github.gustavoflor.githubexplorer.dto.User;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubUserService implements UserService {
    private final GithubClient githubClient;
    private final CacheManager cacheManager;

    @Cacheable(value = "user.findByUsername", key="#username")
    @Override
    public Optional<User> findByUsername(final String username) {
        try {
            final GithubUser githubUser = githubClient.findUserByUsername(username);
            final Set<GithubRepo> githubRepos = githubClient.findReposByUsername(username);
            final Set<GithubFollower> githubFollowers = githubClient.findFollowersByUsername(username);
            evictCacheForGithubFollowers(githubFollowers);
            return Optional.of(User.of(githubUser, githubRepos, githubFollowers));
        } catch (FeignException.FeignClientException.NotFound e) {
            return Optional.empty();
        }
    }

    @CacheEvict(value = "user.findByUsername", key="#username")
    @Override
    public void deleteByUsername(String username) {
        // DO NOTHING, ONLY TO EVICT CACHE
    }

    private void evictCacheForGithubFollowers(Set<GithubFollower> githubFollowers) {
        Optional.ofNullable(cacheManager.getCache("user.findByUsername")).ifPresent(cache -> {
            githubFollowers.stream()
                    .map(GithubFollower::login)
                    .forEach(cache::evict);
        });
    }
}
