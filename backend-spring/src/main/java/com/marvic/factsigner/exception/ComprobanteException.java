package com.marvic.factsigner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ComprobanteException  extends RuntimeException {

    private HttpStatus status;
    private String message;

    public ComprobanteException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ComprobanteException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
