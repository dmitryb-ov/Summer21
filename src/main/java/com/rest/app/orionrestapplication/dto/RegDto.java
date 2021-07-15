package com.rest.app.orionrestapplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.app.orionrestapplication.model.Status;
import com.rest.app.orionrestapplication.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class RegDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date updated;
    private Date created;
    private Status status;

    public static RegDto fromUser(User user) {
        var regDto = new RegDto();
        regDto.setUsername(user.getUsername());
        regDto.setEmail(user.getEmail());
        regDto.setFirstName(user.getFirstName());
        regDto.setLastName(user.getLastName());
        regDto.setUpdated(user.getUpdated());
        regDto.setCreated(user.getCreated());
        regDto.setStatus(user.getStatus());

        return regDto;
    }
}
