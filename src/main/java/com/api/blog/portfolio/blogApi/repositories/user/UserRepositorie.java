package com.api.blog.portfolio.blogApi.repositories.user;


import com.api.blog.portfolio.blogApi.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepositorie extends JpaRepository<User, String> {

    UserDetails findByEmail(String email);
    User getReferenceByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
