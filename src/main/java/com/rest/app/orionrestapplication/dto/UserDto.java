package com.rest.app.orionrestapplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.app.orionrestapplication.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Log4j
@NoArgsConstructor
public class UserDto extends BaseUserDto {

    public UserDto(String username, String firstName, String lastName, String email) {
        super(username, firstName, lastName, email);
    }

    public User toUser() {
        var user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        log.info("Convert entity to user. " + user);
        return user;
    }

    public static UserDto fromUser(User user) {
        var userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        log.info("Convert user to userDto");
        return userDto;
    }
}
