package com.api.blog.portfolio.blogApi.services.user;

import com.api.blog.portfolio.blogApi.controllers.user.dtos.RequestUpdatePasswordUserDto;
import com.api.blog.portfolio.blogApi.controllers.user.dtos.RequestUpdateUserDto;
import com.api.blog.portfolio.blogApi.controllers.user.dtos.RequestUserDto;
import com.api.blog.portfolio.blogApi.entities.Role;
import com.api.blog.portfolio.blogApi.entities.user.EnumStatusActivationUser;
import com.api.blog.portfolio.blogApi.entities.user.User;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.InvalidAuthenticationTokenException;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.InvalidInputException;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.ResourceAlreadyExistsException;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.ResourceNotFoundException;
import com.api.blog.portfolio.blogApi.infra.security.SecurityFilter;
import com.api.blog.portfolio.blogApi.infra.security.TokenService;
import com.api.blog.portfolio.blogApi.repositories.role.RoleRepository;
import com.api.blog.portfolio.blogApi.repositories.user.UserRepositorie;
import com.api.blog.portfolio.blogApi.services.email.EmailDto;
import com.api.blog.portfolio.blogApi.services.email.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepositorie userRepositorie;
    @Autowired
    EmailService emailService;
    @Autowired
    TokenService tokenService;
    @Autowired
    SecurityFilter securityFilter;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepositorie.findByEmail(email);
    }

    //EXTRAS
    public boolean userExistsByEmail(String email) {
        return userRepositorie.existsByEmail(email);
    }
    public void updatePassword(RequestUpdatePasswordUserDto passwordDto, String authToken) throws Exception {
        User user = securityFilter.authToken(authToken);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(passwordDto.oldPassword(), user.getPassword())) {
            throw new InvalidInputException();
        }

        String encryptedNewPassword = passwordEncoder.encode(passwordDto.newPassword());
        user.setPassword(encryptedNewPassword);
        userRepositorie.save(user);
    }
    public void userActivation(String authToken, String token) throws Exception {
        User user;

        if (authToken != null && !authToken.isEmpty()) {
            user = securityFilter.authToken(authToken);
            user.setUserActivation(EnumStatusActivationUser.ACTIVE);
        }  else {
           throw new InvalidAuthenticationTokenException();
        }
        userRepositorie.save(user);

        EmailDto email =  new EmailDto(user.getEmail(), "Welcome!"+user.getUsername(), "Sua conta foi ativada com sucesso!! ");
        Context context = new Context();
        context.setVariable("message", email.body());
        emailService.sendHtmlMessageValidateUser(email, context, "activation");
    }

    //CRUD
    public User createUser(RequestUserDto userDto) throws MessagingException,ResourceAlreadyExistsException {
        if(userExistsByEmail(userDto.email())){
            throw new ResourceAlreadyExistsException();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.password());

        User newUser = User.builder()
                .name(userDto.name())
                .username(userDto.username())
                .email(userDto.email())
                .password(encryptedPassword)
                .createdAt(LocalDateTime.now())
                .userActivation(EnumStatusActivationUser.PENDING)
                .build();


        Optional<Role> userRoleOptional = roleRepository.findByRoleName("USER");
        if (userRoleOptional.isPresent()) {
            Role userRole = userRoleOptional.get();
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            newUser.setRoles(roles);
        } else {
            throw new ResourceNotFoundException();
        }

        userRepositorie.save(newUser);

        EmailDto email =  new EmailDto(userDto.email(), "Welcome!"+userDto.username(), "This body will be ignored, the template will be used instead");

        String token = tokenService.generateTokenValidateUser(newUser);

//        Desconmentar aonde for criado a rota no react
        Context context = new Context();
        context.setVariable("title", email.subject());
        context.setVariable("body", email.body());
        context.setVariable("link", String.format("http://localhost:8081/user/activation/%s", token));
        emailService.sendHtmlMessageValidateUser(email, context, "emailTemplate");
        System.out.printf("http://localhost:8081/user/activation/%s", token);
        return newUser;
    }
    public User getUserId(String token){
        return securityFilter.authToken(token);
    }
    public User updated(RequestUpdateUserDto userDto, String authToken) throws Exception {
        User user = securityFilter.authToken(authToken);

        if (userDto.username() != null && !userDto.username().isEmpty()) {
            System.out.println(!userRepositorie.existsByUsername(userDto.username()));
            if(!userRepositorie.existsByUsername(userDto.username())) user.setUsername(userDto.username());
            else throw new ResourceAlreadyExistsException();
        }
        if (userDto.name() != null && !userDto.name().isEmpty()) {
            user.setName(userDto.name());
        }
        if (userDto.email() != null && !userDto.email().isEmpty()) {
            if(!userRepositorie.existsByEmail(userDto.email())) user.setEmail(userDto.email());
            else throw new ResourceAlreadyExistsException();
        }
        user.setUpdatedAt(LocalDateTime.now());

        userRepositorie.save(user);

        return user;
    }
    public User deleteUser(String token){
        User user = securityFilter.authToken(token);
        this.userRepositorie.delete(user);
        return user;
    }

}
