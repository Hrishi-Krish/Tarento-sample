package com.tarento.sec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GrievanceNotFoundException extends RuntimeException {
    public GrievanceNotFoundException(String message) {
        super(message);
    }

    public GrievanceNotFoundException() {
        super("Grievance not found");
    }

}
