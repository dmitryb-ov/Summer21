package com.rest.app.orionrestapplication.repository;

import com.rest.app.orionrestapplication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
