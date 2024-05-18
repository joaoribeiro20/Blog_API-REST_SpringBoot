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

    private final UserRepositorie userRepositorie;

    @Autowired
    public UserService(UserRepositorie userRepositorie) {
        this.userRepositorie = userRepositorie;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepositorie.findByEmail(email);
    }

    public boolean userExistsByEmail(String email) {
        return userRepositorie.existsByEmail(email);
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


    public void deleteUser(User user){
        this.userRepositorie.delete(user);
    }

}
