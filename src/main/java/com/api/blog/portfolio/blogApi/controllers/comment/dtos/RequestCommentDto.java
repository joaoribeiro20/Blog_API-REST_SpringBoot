package com.api.blog.portfolio.blogApi.controllers.comment.dtos;

public record RequestCommentDto(
        String content,
        String idPublication
) {
}
