package com.api.blog.portfolio.blogApi.controllers.user;

import com.api.blog.portfolio.blogApi.controllers.user.dtos.RequestUserAuthenticationDto;
import com.api.blog.portfolio.blogApi.controllers.user.dtos.ResponseUserTokenDto;
import com.api.blog.portfolio.blogApi.entities.user.User;
import com.api.blog.portfolio.blogApi.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/user")
public class AuthUserController {

    @Autowired
    TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid RequestUserAuthenticationDto data){
        var emailPassword = new UsernamePasswordAuthenticationToken(data.email() ,data.password());
        var auth = this.authenticationManager.authenticate(emailPassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok().body(new ResponseUserTokenDto(token));
    }
}
