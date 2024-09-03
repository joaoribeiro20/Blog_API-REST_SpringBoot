package com.api.blog.portfolio.blogApi.infra.exception.genericExceptions;

public class UnauthorizedAccessException extends RuntimeException{
    //Indica que o acesso a um recurso ou operação não está autorizado.

    public UnauthorizedAccessException(){super("Acesso a recurso ou operação não autorizado");}
    public UnauthorizedAccessException(String message) {super(message);}
}
