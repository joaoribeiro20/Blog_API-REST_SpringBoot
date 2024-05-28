package com.api.blog.portfolio.blogApi.controllers.publication.dtos;

import java.util.List;

public record RequestUpdatePublicationDto(String id, String title, String content, String description, List<String> subjects) {

}
