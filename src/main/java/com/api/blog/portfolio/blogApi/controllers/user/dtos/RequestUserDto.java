package com.api.blog.portfolio.blogApi.controllers.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestUserDto(
        @NotBlank //@NotBlank: Garante que a String não seja nula e tenha pelo menos um caractere não-branco.
        @Pattern(regexp = "^[a-zA-Z]+$", message = "O campo deve conter apenas letras")
        String username,
        @Pattern(regexp = "^[a-zA-Z]+$", message = "O campo deve conter apenas letras")
        @NotBlank //@NotBlank: Garante que a String não seja nula e tenha pelo menos um caractere não-branco.
        String name,
        @Email
        String email,
        @Size(min = 4, max = 14) //@Size: Garante que o tamanho de uma Collection, Map, array, ou String esteja dentro dos limites especificados.
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = "The field must contain at least one letter and one number")
        String password
) {
}
