package com.api.blog.portfolio.blogApi.controllers.user;

import com.api.blog.portfolio.blogApi.controllers.user.dtos.*;
import com.api.blog.portfolio.blogApi.entities.user.User;
import com.api.blog.portfolio.blogApi.services.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping()
    @Transactional
    public ResponseEntity CreateNewUser(@RequestBody @Valid RequestUserDto user,  UriComponentsBuilder uriBuilder) throws Exception {
        User newUser = userService.createUser(user);
        var uri = uriBuilder.path("/user/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseUserDto(newUser));
    }
    @GetMapping()
    public ResponseEntity getUser(@RequestHeader("Authorization") String authToken) throws Exception {
        User user = userService.getUserId(authToken);
        return ResponseEntity.ok().body(user);
    }
    @PutMapping
    public ResponseEntity updateUser(@RequestBody RequestUpdateUserDto user, @RequestHeader("Authorization") String authToken) throws Exception {
        User result = userService.updated(user, authToken);
        return ResponseEntity.ok().body(new ResponseUpdateUserDto(result));
    }
    @PatchMapping
    public  ResponseEntity updatePassword(@RequestBody RequestUpdatePasswordUserDto password,@RequestHeader("Authorization") String authToken) throws Exception {
        userService.updatePassword(password,authToken);
        return ResponseEntity.ok().body("Password atualizada com sucesso");
    }
    @DeleteMapping
    public  ResponseEntity delete(@RequestHeader("Authorization") String authToken){
        User result = userService.deleteUser(authToken);
        return ResponseEntity.ok().body(result);
    }
    @PostMapping("/activation")
    public ResponseEntity userActivation(@RequestHeader("Authorization") String authToken, @RequestParam(value = "token", required = false, defaultValue = "") String token) throws Exception {
        if (authToken != null && !authToken.isEmpty()) {
            userService.userActivation(authToken, "");
        } else if (token != null && !token.isEmpty()) {
            userService.userActivation("", token);
        } else {
            // Lógica para tratamento de erro ou retorno adequado
        }
        return ResponseEntity.ok().body("Ativação da conta do usuario realizada com sucesso!1");
        // Retorna ResponseEntity adequado (status, corpo etc.)
    }
}
