package com.api.blog.portfolio.blogApi.infra.security;

import com.api.blog.portfolio.blogApi.entities.user.EnumStatusActivationUser;
import com.api.blog.portfolio.blogApi.entities.user.User;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.InvalidAuthenticationTokenException;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.StateUserException;
import com.api.blog.portfolio.blogApi.repositories.user.UserRepositorie;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepositorie userRepositorie;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            String loginEmail;
            try {
                loginEmail = tokenService.validateToken(token);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{" + "\"detail\": \"Token invalido ou expirado\"" + "}");
                return;
            }

            var user = userRepositorie.findByEmail(loginEmail);
            var statusUser = userRepositorie.getReferenceByEmail(loginEmail);

            if (statusUser == null || statusUser.getUserActivation() != EnumStatusActivationUser.ACTIVE) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{" + "\"detail\": \"Usuario pendente ou inativo\"" + "}");
                return;
            }

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    public User authToken(String token){
        String tokenValidation = tokenService.validateToken(token.replace("Bearer ", ""));
        if (tokenValidation == null) {
            throw new InvalidAuthenticationTokenException();
        }
        return userRepositorie.getReferenceByEmail(tokenValidation);
    }
}
