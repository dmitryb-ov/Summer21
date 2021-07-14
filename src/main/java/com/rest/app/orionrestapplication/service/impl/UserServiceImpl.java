package com.rest.app.orionrestapplication.service.impl;

import com.rest.app.orionrestapplication.model.Role;
import com.rest.app.orionrestapplication.model.Status;
import com.rest.app.orionrestapplication.model.User;
import com.rest.app.orionrestapplication.repository.RoleRepository;
import com.rest.app.orionrestapplication.repository.UserRepository;
import com.rest.app.orionrestapplication.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
public class UserServiceImpl implements UserService {
    private static final String IS_NULL = " is null";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        var role = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);

        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var registeredUser = userRepository.save(user);

        log.info("User registered: ResponseId: " + registeredUser.getId());
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = userRepository.findAll();
        log.info("Get all users. Rows=" + userList.size());
        return userList;
    }

    @Override
    public User findByUserName(String userName) {
        var user = userRepository.findByUsername(userName);
        if (user == null) {
            log.warn("User with name=" + userName + IS_NULL);
            return null;
        }
        log.info("Get user by name=" + userName);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        var user = userRepository.findByEmail(email);
        if (user == null) {
            log.warn("User with email=" + email + IS_NULL);
            return null;
        }
        log.info("Get user by email=" + email);
        return user;
    }

    @Override
    public User findById(Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("User with id=" + id + IS_NULL);
            return null;
        }
        log.info("Get user by id=" + id);
        return user;
    }

    @Override
    public void delete(Long id) {
        log.info("Delete user with id=" + id);
        userRepository.deleteById(id);
    }
}
