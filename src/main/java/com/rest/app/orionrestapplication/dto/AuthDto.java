package com.rest.app.orionrestapplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthDto {
    private String username;
    private String password;
    private String token;
}
