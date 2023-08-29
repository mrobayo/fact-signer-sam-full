package com.marvic.factsigner.exception;

import org.springframework.http.HttpStatus;

public class RequiredKeyException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public RequiredKeyException() {
        this.status = HttpStatus.CONFLICT;
        this.message = "Key/Id es requerido";
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

