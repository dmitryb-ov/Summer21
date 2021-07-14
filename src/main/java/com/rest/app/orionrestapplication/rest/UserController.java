package com.rest.app.orionrestapplication.rest;

import com.rest.app.orionrestapplication.annotation.Monitor;
import com.rest.app.orionrestapplication.dto.UserDto;
import com.rest.app.orionrestapplication.model.MonitorType;
import com.rest.app.orionrestapplication.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users/")
@Log4j
public class UserController {
    private static final String URL_VALUE = "/users/";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    @Monitor(requestName = MonitorType.GET)
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        log.info("Called [" + URL_VALUE + id);
        var user = userService.findById(id);

        if (user == null) {
            log.warn("User with id=" + id + " is null");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        var result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
