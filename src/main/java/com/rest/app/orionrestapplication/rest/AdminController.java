package com.rest.app.orionrestapplication.rest;

import com.rest.app.orionrestapplication.annotation.Monitor;
import com.rest.app.orionrestapplication.dto.AdminUserDto;
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
@RequestMapping(value = "/admin/")
@Log4j
public class AdminController {
    private static final String URL_VALUE = "/admin/";

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    @Monitor(requestName = MonitorType.GET)
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        log.info("Called [" + URL_VALUE + "users/" + id + "]");
        var user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var result = AdminUserDto.fromAdminUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
