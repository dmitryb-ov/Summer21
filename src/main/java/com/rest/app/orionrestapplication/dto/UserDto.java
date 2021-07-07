package com.rest.app.orionrestapplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.app.orionrestapplication.model.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends BaseUserDto {
//    private Long id;
//    private String username;
//    private String firstName;
//    private String lastName;
//    private String email;

    public User toUser(){
        var user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return user;
    }

    public static UserDto fromUser(User user){
        var userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
