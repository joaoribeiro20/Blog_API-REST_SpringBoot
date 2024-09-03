package com.api.blog.portfolio.blogApi.infra.exception;

import org.springframework.http.HttpStatus;

public record DefaultResponseErrorMessage(
        String title,
        HttpStatus status,
        String detail,
        String instance
) {
}
