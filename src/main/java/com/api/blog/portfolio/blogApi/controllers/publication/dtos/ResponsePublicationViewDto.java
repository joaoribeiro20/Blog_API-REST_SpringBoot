package com.api.blog.portfolio.blogApi.controllers.publication.dtos;

import com.api.blog.portfolio.blogApi.controllers.comment.dtos.ResponseCommentDto;
import com.api.blog.portfolio.blogApi.controllers.user.dtos.ResponseGenericUserDto;
import com.api.blog.portfolio.blogApi.entities.Comment;
import com.api.blog.portfolio.blogApi.entities.Publication;

import java.util.List;
import java.util.stream.Collectors;

public record ResponsePublicationViewDto(String id,
                                         String title,
                                         String content,
                                         String url,
                                         String description,
                                         List<String> subjects,
                                         ResponseGenericUserDto user,
                                         List<ResponseCommentDto> comments) {
    public ResponsePublicationViewDto(Publication publication) {
        this(
                publication.getId(),
                publication.getTitle(),
                publication.getContent(),
                publication.getUrl(),
                publication.getDescription(),
                publication.getSubjects(),
                new ResponseGenericUserDto(publication.getUser()),
                publication.getComments().stream()
                        .map(ResponseCommentDto::new)
                        .collect(Collectors.toList())
        );
    }
}
