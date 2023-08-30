package com.marvic.factsigner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceExistsException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public ResourceExistsException(String message) {
        this.status = HttpStatus.CONFLICT;
        this.message = String.format("[%s] - ya existe", message);
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
