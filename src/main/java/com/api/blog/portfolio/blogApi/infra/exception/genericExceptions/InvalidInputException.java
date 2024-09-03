package com.api.blog.portfolio.blogApi.infra.exception.genericExceptions;

public class InvalidInputException extends RuntimeException{
    //Indica que a entrada fornecida é inválida ou não atende aos critérios esperados.

    public InvalidInputException(){super("Dados inválidos");}
    public InvalidInputException(String message) {super(message);}

}
