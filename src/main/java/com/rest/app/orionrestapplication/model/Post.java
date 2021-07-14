package com.rest.app.orionrestapplication.model;

import com.rest.app.orionrestapplication.util.DateUtil;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "rest_posts")
public class Post extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    @Column(name = "author_post")
    private String text;

    @Column(name = "lastRequestDate")
    private Date lastRequest;

    @Column(name = "deleteDate")
    private Date deleteDate;

    @PostLoad
    protected void onLoad() {
        lastRequest = new Date();
    }

    @Override
    protected void onUpdate() {
        lastRequest = new Date();
        deleteDate = DateUtil.calculateDeletedDate(lastRequest);
        super.onUpdate();
    }


}
