package com.api.blog.portfolio.blogApi.controllers.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public record RequestUpdateUserDto(@NotBlank
                                   //@NotBlank: Garante que a String não seja nula e tenha pelo menos um caractere não-branco.
                                   @Pattern(regexp = "^[a-zA-Z]+$", message = "O campo deve conter apenas letras")
                                   String username,
                                   @Pattern(regexp = "^[a-zA-Z]+$", message = "O campo deve conter apenas letras")
                                   @NotBlank //@NotBlank: Garante que a String não seja nula e tenha pelo menos um caractere não-branco.
                                   String name,
                                   @Email
                                   String email) {
}
