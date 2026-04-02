package com.teste.primeiro_exemplo.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST)
public class InvalidProductException extends RuntimeException {
    
    public InvalidProductException(String mensagem) {
        super(mensagem);
    }
}
