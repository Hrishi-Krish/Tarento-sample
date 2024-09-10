package com.tarento.sec.exception;

public class RoleNotAllowedException extends RuntimeException {
    public RoleNotAllowedException(String message) {
        super(message);
    }

    public RoleNotAllowedException() {
        super("Role not allowed");
    }
}