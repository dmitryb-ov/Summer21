package com.rest.app.orionrestapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseUserDto extends BaseDto {
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String email;

    BaseUserDto(Long id, String username, String firstName, String lastName, String email) {
        super(id);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
