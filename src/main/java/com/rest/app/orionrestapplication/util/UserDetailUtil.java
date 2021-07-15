package com.rest.app.orionrestapplication.util;

import lombok.extern.log4j.Log4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class UserDetailUtil {

    public UserDetails getUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (UserDetails) authentication.getPrincipal();
        log.info("Get user from context. Username="+userDetails.getUsername());
        return userDetails;
    }
}
