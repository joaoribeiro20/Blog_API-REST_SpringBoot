package com.api.blog.portfolio.blogApi.controllers.user.dtos;

import com.api.blog.portfolio.blogApi.entities.user.User;

public record ResponseGenericUserDto(
        String id,
        String name

) {
    public ResponseGenericUserDto(User user) {
        this(
                user.getId(),
                user.getName()
        );
    }
}
