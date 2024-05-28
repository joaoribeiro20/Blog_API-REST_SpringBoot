package com.api.blog.portfolio.blogApi.controllers.publication.dtos;

import com.api.blog.portfolio.blogApi.controllers.user.dtos.ResponseGenericUserDto;
import com.api.blog.portfolio.blogApi.controllers.user.dtos.ResponseUserDto;
import com.api.blog.portfolio.blogApi.entities.Publication;
import com.api.blog.portfolio.blogApi.entities.user.User;

import java.util.List;

public record ResponsePublicationFeedDto(
        String id,
        String title,
        String url,
        String description,
        List<String> subjects,
        ResponseGenericUserDto  user
) {
    public ResponsePublicationFeedDto(Publication publication) {
        this(
                publication.getId(),
                publication.getTitle(),
                publication.getUrl(),
                publication.getDescription(),
                publication.getSubjects(),
                new ResponseGenericUserDto(publication.getUser())
        );
    }
}

