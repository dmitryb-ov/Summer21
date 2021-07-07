package com.rest.app.orionrestapplication.dto;

import lombok.Data;

@Data
public abstract class BaseUserDto {
    protected Long id;
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String email;
}
