package com.api.blog.portfolio.blogApi.repositories.user;


import com.api.blog.portfolio.blogApi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserRepositorie extends JpaRepository<User, String> {

    UserDetails findByEmail(String email);
    Optional<User> findByUsername(String username);
}
