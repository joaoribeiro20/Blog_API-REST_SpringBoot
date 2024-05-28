package com.api.blog.portfolio.blogApi.services.user;

import com.api.blog.portfolio.blogApi.controllers.user.dtos.RequestUpdatePasswordUserDto;
import com.api.blog.portfolio.blogApi.controllers.user.dtos.RequestUpdateUserDto;
import com.api.blog.portfolio.blogApi.controllers.user.dtos.RequestUserDto;
import com.api.blog.portfolio.blogApi.entities.user.EnumStatusActivationUser;
import com.api.blog.portfolio.blogApi.entities.user.User;
import com.api.blog.portfolio.blogApi.infra.security.SecurityFilter;
import com.api.blog.portfolio.blogApi.infra.security.TokenService;
import com.api.blog.portfolio.blogApi.repositories.user.UserRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepositorie userRepositorie;

    @Autowired
    SecurityFilter securityFilter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepositorie.findByEmail(email);
    }
    public boolean userExistsByEmail(String email) {
        return userRepositorie.existsByEmail(email);
    }
    public User getUserId(String token) throws Exception {
        return securityFilter.authToken(token);
    }
    public User createUser(RequestUserDto user) throws Exception {
        if (userExistsByEmail(user.email())) {
            throw new Exception("User already exists with email: " + user.email());
        }

        User newUser = new User(user);

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.password());

        newUser.setPassword(encryptedPassword);
        userRepositorie.save(newUser);

        return newUser;
    }
    public User deleteUser(String token){
        User user = securityFilter.authToken(token);
        this.userRepositorie.delete(user);
        return user;
    }
    public void updatePassword(RequestUpdatePasswordUserDto passwordDto, String authToken) throws Exception {
        User user = securityFilter.authToken(authToken);  // Obter o usuário pelo token de autenticação
        if (user == null) {
            throw new IllegalArgumentException("Invalid authentication token");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Verificar se a senha antiga fornecida corresponde à senha armazenada
        if (!passwordEncoder.matches(passwordDto.oldPassword(), user.getPassword())) {
            throw new Exception("Senha antiga incorreta");
        }

        // Codificar a nova senha e atualizar o usuário
        String encryptedNewPassword = passwordEncoder.encode(passwordDto.newPassword());
        user.setPassword(encryptedNewPassword);
        userRepositorie.save(user);
    }
    public User updated(RequestUpdateUserDto userDto, String authToken) throws Exception {
        User user = securityFilter.authToken(authToken);
        if (user == null) {
            throw new IllegalArgumentException("Invalid authentication token");
        }

        // Atualizando os dados do usuário com os valores de `userDto`
        if (userDto.username() != null && !userDto.username().isEmpty()) {
            System.out.println(!userRepositorie.existsByUsername(userDto.username()));
            if(!userRepositorie.existsByUsername(userDto.username())) user.setUsername(userDto.username());
            else throw new Exception("Ja existe usuario com esse username");
        }
        if (userDto.name() != null && !userDto.name().isEmpty()) {
            user.setName(userDto.name());
        }
        if (userDto.email() != null && !userDto.email().isEmpty()) {
            if(!userRepositorie.existsByEmail(userDto.email())) user.setEmail(userDto.email());
            else throw new Exception("Ja existe usuario com esse email");
        }
        user.setUpdatedAt(LocalDateTime.now());
        // Salvando o usuário atualizado
        userRepositorie.save(user);

        return user;
    }
    public void userActivation(String authToken, String token) throws Exception {
        User user;
        if (authToken != null && !authToken.isEmpty()) {
            user = securityFilter.authToken(authToken);
            user.setUserActivation(EnumStatusActivationUser.ACTIVE);
        } else if (token != null && !token.isEmpty()) {
            user = securityFilter.authToken(authToken);
            user.setUserActivation(EnumStatusActivationUser.ACTIVE);
        } else {
           throw new Exception("Token nao informado");
        }
        userRepositorie.save(user);
        // Retorna ResponseEntity adequado (status, corpo etc.)
    }
}
