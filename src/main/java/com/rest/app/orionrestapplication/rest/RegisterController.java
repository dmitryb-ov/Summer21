package com.rest.app.orionrestapplication.rest;

import com.rest.app.orionrestapplication.annotation.Monitor;
import com.rest.app.orionrestapplication.dto.RegDto;
import com.rest.app.orionrestapplication.exception.EntityAlreadyExistException;
import com.rest.app.orionrestapplication.model.MonitorType;
import com.rest.app.orionrestapplication.model.User;
import com.rest.app.orionrestapplication.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reg/")
@Log4j
public class RegisterController {
    private static final String URL_VALUE = "/reg/";

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "new")
    @Monitor(requestName = MonitorType.POST)
    public ResponseEntity<RegDto> register(@RequestBody RegDto regDto) {
        log.info("Called [" + URL_VALUE + "new " + "PostDto: " + regDto.toString());
        var userUsername = userService.findByUserName(regDto.getUsername());
        var userEmail = userService.findByEmail(regDto.getEmail());

        if (userUsername != null) {
            log.warn("Username " + userUsername + "already exist");
            throw new EntityAlreadyExistException("Username already exists");
        }

        if (userEmail != null) {
            log.warn("Email " + userEmail + "already exist");
            throw new EntityAlreadyExistException("Email already exist");
        }

        var result = new User();
        result.setUsername(regDto.getUsername());
        result.setFirstName(regDto.getFirstName());
        result.setLastName(regDto.getLastName());
        result.setEmail(regDto.getEmail());
        result.setPassword(regDto.getPassword());

        var registeredUser = userService.register(result);

        var response = RegDto.fromUser(registeredUser);

        return ResponseEntity.ok(response);
    }
}
