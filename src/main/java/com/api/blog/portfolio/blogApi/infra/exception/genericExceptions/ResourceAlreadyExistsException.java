package com.api.blog.portfolio.blogApi.infra.exception.genericExceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    //Indica que o recurso que está tentando ser criado já existe.

    public ResourceAlreadyExistsException(){super("Dados ja existentes no sistema");}
    public ResourceAlreadyExistsException(String message) {super(message);}
}

