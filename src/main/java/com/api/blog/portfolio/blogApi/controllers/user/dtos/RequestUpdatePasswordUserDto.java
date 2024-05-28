package com.api.blog.portfolio.blogApi.controllers.user.dtos;

public record RequestUpdatePasswordUserDto(
        String oldPassword,
        String newPassword
) {
}
