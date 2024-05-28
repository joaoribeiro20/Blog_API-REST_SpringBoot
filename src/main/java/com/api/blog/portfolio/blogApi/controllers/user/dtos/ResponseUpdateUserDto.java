package com.api.blog.portfolio.blogApi.controllers.user.dtos;

import com.api.blog.portfolio.blogApi.entities.user.User;

import java.time.LocalDateTime;

public record ResponseUpdateUserDto(String id,
                                    String username,
                                    String name,
                                    String email,
                                    LocalDateTime createdAt,
                                    LocalDateTime updatedAt) {

    public ResponseUpdateUserDto(User newUser) {
        this(newUser.getId(),
                newUser.getUsername(),
                newUser.getName(),
                newUser.getEmail(),
                newUser.getCreatedAt(),
                newUser.getUpdatedAt());
    }
}
