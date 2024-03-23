package com.miromax.cinema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SeatRowNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SeatRowNotFoundException(String message) {
        super(message);
    }
}
