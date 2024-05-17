package com.api.blog.portfolio.blogApi.services.user;

import com.api.blog.portfolio.blogApi.controllers.user.dtos.RequestUserDto;
import com.api.blog.portfolio.blogApi.entities.User;
import com.api.blog.portfolio.blogApi.repositories.user.UserRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepositorie userRepositorie;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepositorie.findByEmail(email);
    }

    public boolean validateExist(String username){
        Optional existUser = this.userRepositorie.findByUsername(username);
        System.out.println(existUser);
        return existUser.isPresent();
    }
    public User createUser(RequestUserDto user) throws Exception {
        System.out.println(validateExist(user.email()));
        if (!validateExist(user.email())) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(user.password());
            System.out.println(encryptedPassword);
            RequestUserDto newUserDto = new RequestUserDto(user.username(), user.email(), user.name(), encryptedPassword);
            User newUser = new User(newUserDto);
            this.saveUser(newUser);
            return newUser;
        } else {
            throw new Exception("User already exists with email: " + user.email());
        }
    }
    public void saveUser(User user) throws Exception {
            this.userRepositorie.save(user);
    }
    public void deleteUser(User user){
        this.userRepositorie.delete(user);
    }

}
