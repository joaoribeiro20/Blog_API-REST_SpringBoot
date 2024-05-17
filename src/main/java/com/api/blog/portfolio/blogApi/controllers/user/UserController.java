package com.api.blog.portfolio.blogApi.controllers.user;

import com.api.blog.portfolio.blogApi.controllers.user.dtos.RequestUserAuthenticationDto;
import com.api.blog.portfolio.blogApi.controllers.user.dtos.RequestUserDto;
import com.api.blog.portfolio.blogApi.controllers.user.dtos.ResponseUserDto;
import com.api.blog.portfolio.blogApi.entities.User;
import com.api.blog.portfolio.blogApi.services.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("auth/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    //ROTA PARA CRIAÇÃO DE UM NOVO USUARIO
    @PostMapping()
    @Transactional
    public ResponseEntity CreateNewUser(@RequestBody @Valid RequestUserDto user,  UriComponentsBuilder uriBuilder) throws Exception {
        User newUser = userService.createUser(user);
        var uri = uriBuilder.path("/customer/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid RequestUserAuthenticationDto data){
        var emailPassword = new UsernamePasswordAuthenticationToken(data.email() ,data.password());
        return ResponseEntity.ok().body(emailPassword);
    }

}
