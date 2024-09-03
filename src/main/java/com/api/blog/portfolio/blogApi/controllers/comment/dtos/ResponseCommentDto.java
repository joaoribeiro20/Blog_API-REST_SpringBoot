package com.api.blog.portfolio.blogApi.controllers.comment.dtos;

import com.api.blog.portfolio.blogApi.entities.Comment;

public record ResponseCommentDto(
        String id,
        String content,
        String username,
        String idPublication,
        String idUser
) {
    public ResponseCommentDto(Comment comment){
        this(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getPublication().getId(),
                comment.getUser().getId()
        );
    }
}
