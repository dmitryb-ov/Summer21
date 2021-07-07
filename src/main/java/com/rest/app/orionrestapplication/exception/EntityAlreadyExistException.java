package com.rest.app.orionrestapplication.exception;

import org.springframework.security.core.AuthenticationException;

public class EntityAlreadyExistException extends AuthenticationException {

    public EntityAlreadyExistException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EntityAlreadyExistException(String msg) {
        super(msg);
    }
}
