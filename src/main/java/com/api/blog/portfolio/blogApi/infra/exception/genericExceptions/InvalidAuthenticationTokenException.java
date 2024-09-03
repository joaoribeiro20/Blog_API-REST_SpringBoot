package com.api.blog.portfolio.blogApi.infra.exception.genericExceptions;

public class InvalidAuthenticationTokenException extends RuntimeException{
    //Indica que a entrada fornecida é inválida ou não atende aos critérios esperados.

    public InvalidAuthenticationTokenException(){super("Token invalido ou expirado");}
    public InvalidAuthenticationTokenException(String message) {super(message);}

}

