package com.api.blog.portfolio.blogApi.controllers.user.dtos;

import com.api.blog.portfolio.blogApi.entities.user.EnumStatusActivationUser;
import com.api.blog.portfolio.blogApi.entities.user.User;

import java.time.LocalDateTime;

public record ResponseUserDto(
                              String id,
                              String username,
                              String name,
                              String email,
                              EnumStatusActivationUser userActivation,
                              String password,
                              LocalDateTime createdAt
                              ) {
    public ResponseUserDto(User newUser) {
        this(newUser.getId(),
                newUser.getUsername(),
                newUser.getName(),
                newUser.getEmail(),
                newUser.getUserActivation(),
                newUser.getPassword(),
                newUser.getCreatedAt());
    }


}
