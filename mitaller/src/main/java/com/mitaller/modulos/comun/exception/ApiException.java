package com.mitaller.modulos.comun.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException extends Throwable {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus status;

    private final ZonedDateTime timestamp;

    public ApiException(String message,
                        Throwable throwable,
                        HttpStatus status,
                        ZonedDateTime timestamp) {
        this.message = message;
        this.throwable = throwable;
        this.status = status;
        this.timestamp = timestamp;
    }



}
