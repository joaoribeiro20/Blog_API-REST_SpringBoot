package com.api.blog.portfolio.blogApi.infra.exception.genericExceptions;


public class ResourceNotFoundException extends RuntimeException{
    //Indica que um recurso específico não foi encontrado.

    public ResourceNotFoundException(){super("Dado Não encontrado");}
    public ResourceNotFoundException(String message) {super(message);}
}
