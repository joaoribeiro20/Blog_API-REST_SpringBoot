package com.api.blog.portfolio.blogApi.controllers.comment.dtos;

public record RequestGenericCommentDto (
        String content,
        String commentId
){
}
