package com.hb.cda.thymeleafproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hb.cda.thymeleafproject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findByUsername(String username);
}

