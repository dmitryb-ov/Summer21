package com.rest.app.orionrestapplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.app.orionrestapplication.model.Status;
import com.rest.app.orionrestapplication.model.User;
import lombok.Data;
import lombok.extern.log4j.Log4j;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Log4j
public class AdminUserDto extends BaseUserDto {
    private String status;

    public User toAdminUser() {
        var user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));

        log.info("Convert to admin user. " + user.getId());
        return user;
    }

    public static AdminUserDto fromAdminUser(User user) {
        var adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setUsername(user.getUsername());
        adminUserDto.setFirstName(user.getFirstName());
        adminUserDto.setLastName(user.getLastName());
        adminUserDto.setEmail(user.getEmail());
        adminUserDto.setStatus(user.getStatus().name());

        log.info("Convert dto from adminUser");
        return adminUserDto;
    }
}
