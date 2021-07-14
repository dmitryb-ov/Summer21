package com.rest.app.orionrestapplication.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rest_requests_history")
@Data
public class History extends BaseEntity {

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "username")
    private String username;

    @Column(name = "ip")
    private String ip;

    @Column(name = "requestType")
    private String requestType;

    @Column(name = "className")
    private String className;

    @Column(name = "methodName")
    private String methodName;

    @Override
    public String toString() {
        return "History{" +
                "id='" + getId() + '\'' +
                "username='" + username + '\'' +
                ", ip='" + ip + '\'' +
                ", requestType='" + requestType + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", updated='" + getUpdated() + '\'' +
                ", created='" + getCreated() + '\'' +
                '}';
    }
}
