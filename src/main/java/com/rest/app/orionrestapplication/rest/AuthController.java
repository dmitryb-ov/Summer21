package com.rest.app.orionrestapplication.rest;

import com.rest.app.orionrestapplication.annotation.Monitor;
import com.rest.app.orionrestapplication.dto.AuthDto;
import com.rest.app.orionrestapplication.model.MonitorType;
import com.rest.app.orionrestapplication.security.jwt.JwtTokenProvider;
import com.rest.app.orionrestapplication.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth/")
@Log4j
public class AuthController {
    private static final String URL_VALUE = "/auth/";

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    @Monitor(requestName = MonitorType.POST)
    public ResponseEntity<AuthDto> login(@RequestBody AuthDto dto) {
        try {
            log.info("Called [" + URL_VALUE + "login " + "AuthDto: " + dto.toString());
            String username = dto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, dto.getPassword()));
            var user = userService.findByUserName(username);

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());
            if (token.isBlank()) {
                log.warn("Token is empty");
            } else {
                log.info("Token set");
            }

            var response = new AuthDto();
            response.setUsername(username);
            response.setPassword("hidden");
            response.setToken(token);
//            Map<Object, Object> response = new HashMap<>();
//            response.put("username", username);
//            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            log.error("Invalid username or password", e.getCause());
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
