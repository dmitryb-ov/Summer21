package com.rest.app.orionrestapplication.rest;

import com.rest.app.orionrestapplication.dto.RegDto;
import com.rest.app.orionrestapplication.exception.EntityAlreadyExistException;
import com.rest.app.orionrestapplication.model.User;
import com.rest.app.orionrestapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/reg/")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "new")
    public ResponseEntity register(@RequestBody RegDto regDto) {
        var userUsername = userService.findByUserName(regDto.getUsername());
        var userEmail = userService.findByEmail(regDto.getEmail());

        if (userUsername != null) {
            throw new EntityAlreadyExistException("Username already exists");
        }

        if (userEmail != null) {
            throw new EntityAlreadyExistException("Email already exists");
        }

        var result = new User();
        result.setUsername(regDto.getUsername());
        result.setFirstName(regDto.getFirstName());
        result.setLastName(regDto.getLastName());
        result.setEmail(regDto.getEmail());
        result.setPassword(regDto.getPassword());

        var registeredUser = userService.register(result);

        Map<Object, Object> response = new HashMap<>();
        response.put("username", registeredUser.getUsername());
        response.put("firstName", registeredUser.getFirstName());
        response.put("lastName", registeredUser.getLastName());
        response.put("email", registeredUser.getEmail());
//        response.put("updated", registeredUser.getUpdated());
//        response.put("created", registeredUser.getCreated());
        response.put("status", registeredUser.getStatus());

        return ResponseEntity.ok(response);
    }
}
