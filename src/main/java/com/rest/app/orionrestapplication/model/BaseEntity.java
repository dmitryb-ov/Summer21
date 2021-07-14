package com.rest.app.orionrestapplication.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", updatable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;

    @PrePersist
    protected void onCreate(){
        if(this.created == null) {
            updated = created = new Date();
        }
    }

    @PreUpdate
    protected void onUpdate(){
        updated = new Date();
    }
}
