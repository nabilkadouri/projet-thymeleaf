package com.hb.cda.thymeleafproject.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hb.cda.thymeleafproject.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

    private UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("User not found")
        );
    }

}

