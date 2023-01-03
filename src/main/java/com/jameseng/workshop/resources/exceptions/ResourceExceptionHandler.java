package com.jameseng.workshop.resources.exceptions;

import com.jameseng.workshop.services.exeptions.DatabaseException;
import com.jameseng.workshop.services.exeptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice // interceptar exceções e executar tratamento
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) // interceptar exceção ResourceNotFoundException
    public ResponseEntity<StandardError> ResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {

        String error = "Resource not found.";
        HttpStatus status = HttpStatus.NOT_FOUND; // código 404
        StandardError errorObject = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(errorObject);
    }

    @ExceptionHandler(DatabaseException.class) // interceptar exceção ResourceNotFoundException
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {

        String error = "Database error.";
        HttpStatus status = HttpStatus.BAD_REQUEST; // código 400
        StandardError errorObject = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(errorObject);
    }
}
