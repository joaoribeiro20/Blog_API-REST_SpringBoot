package com.api.blog.portfolio.blogApi.infra.security;

import com.api.blog.portfolio.blogApi.entities.User;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Value("${api.security.token.secret")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

        } catch () {

        }

    }

}
