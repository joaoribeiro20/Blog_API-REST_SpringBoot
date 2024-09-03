package com.api.blog.portfolio.blogApi.controllers.role;

public record AssignRoleRequest(
        String role,
        String user
) {
}
