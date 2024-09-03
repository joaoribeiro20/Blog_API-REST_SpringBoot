package com.api.blog.portfolio.blogApi.infra.exception;

import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<DefaultResponseErrorMessage> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception, HttpServletRequest request) {
        String instanceUrl = request.getRequestURL().toString();
        DefaultResponseErrorMessage rest = new DefaultResponseErrorMessage("Resource Already Exists", HttpStatus.CONFLICT, exception.getMessage(), instanceUrl);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(rest);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<DefaultResponseErrorMessage> handleInvalidInputException(InvalidInputException exception, HttpServletRequest request) {
        String instanceUrl = request.getRequestURL().toString();
        DefaultResponseErrorMessage rest = new DefaultResponseErrorMessage("Invalid Input", HttpStatus.BAD_REQUEST, exception.getMessage(), instanceUrl);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rest);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DefaultResponseErrorMessage> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        String instanceUrl = request.getRequestURL().toString();
        DefaultResponseErrorMessage rest = new DefaultResponseErrorMessage("Resource Not Found", HttpStatus.NOT_FOUND, exception.getMessage(), instanceUrl);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rest);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<DefaultResponseErrorMessage> handleUnauthorizedAccessException(UnauthorizedAccessException exception, HttpServletRequest request) {
        String instanceUrl = request.getRequestURL().toString();
        DefaultResponseErrorMessage rest = new DefaultResponseErrorMessage("Unauthorized Access", HttpStatus.UNAUTHORIZED, exception.getMessage(), instanceUrl);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(rest);
    }
    @ExceptionHandler(InvalidAuthenticationTokenException.class)
    public ResponseEntity<DefaultResponseErrorMessage> handleInvalidAuthenticationTokenException(InvalidAuthenticationTokenException exception, HttpServletRequest request) {
        String instanceUrl = request.getRequestURL().toString();
        DefaultResponseErrorMessage rest = new DefaultResponseErrorMessage("Unauthorized Access", HttpStatus.UNAUTHORIZED, exception.getMessage(), instanceUrl);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(rest);
    }

    // Método para capturar exceções nativas do Java
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DefaultResponseErrorMessage> handleRuntimeException(RuntimeException exception, HttpServletRequest request) {
        String instanceUrl = request.getRequestURL().toString();
        DefaultResponseErrorMessage rest = new DefaultResponseErrorMessage("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", instanceUrl);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rest);
    }



    //@ExceptionHandler(MethodArgumentNotValidException.class)
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//        DefaultResponseErrorMessage rest = new DefaultResponseErrorMessage(HttpStatus.MULTI_STATUS, "Erro interno no servidor");
//        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(rest);
//    }
}