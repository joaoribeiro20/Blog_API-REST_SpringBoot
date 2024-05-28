package com.api.blog.portfolio.blogApi.controllers.publication.dtos;

import java.util.List;

public record RequestPublicationDto(String title, String content, String url, String description, List<String> subjects) {
}
