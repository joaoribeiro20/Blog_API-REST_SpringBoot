package com.api.blog.portfolio.blogApi.controllers.publication.dtos;

import com.api.blog.portfolio.blogApi.entities.Publication;

import java.util.List;

public record ResponseUpdatePublicationDto(String id, String title, String content, String description, List<String> subjects) {
    public ResponseUpdatePublicationDto(Publication rest) {
        this(
                rest.getId(),
                rest.getTitle(),
                rest.getContent(),
                rest.getDescription(),
                rest.getSubjects());
    }
}
