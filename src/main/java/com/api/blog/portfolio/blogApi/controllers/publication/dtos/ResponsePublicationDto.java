package com.api.blog.portfolio.blogApi.controllers.publication.dtos;

import com.api.blog.portfolio.blogApi.controllers.user.dtos.ResponseUserDto;
import com.api.blog.portfolio.blogApi.entities.Publication;

import java.util.List;

public record ResponsePublicationDto(
        String title,
        String content,
        String url,
        String description,
        List<String> subjects,
        ResponseUserDto  user
) {
    public ResponsePublicationDto(Publication createPublication) {
        this(
                createPublication.getTitle(),
                createPublication.getContent(),
                createPublication.getUrl(),
                createPublication.getDescription(), // Corrigido o nome do campo: 'descriprion' para 'description'
                createPublication.getSubjects(),
                new ResponseUserDto(createPublication.getUser())
        );
    }
}
