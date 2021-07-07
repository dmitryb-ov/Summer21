package com.rest.app.orionrestapplication.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rest_roles")
@Data
public class Role extends BaseEntity{

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}
