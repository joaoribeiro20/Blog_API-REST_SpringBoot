package com.api.blog.portfolio.blogApi.infra.exception.genericExceptions;

public class StateUserException extends RuntimeException {
    public StateUserException() {
        super("Usuário pendente ou inativo");
    }

    public StateUserException(String message) {
        super(message);
    }
}