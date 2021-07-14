package com.rest.app.orionrestapplication.service;

import com.rest.app.orionrestapplication.model.Post;

import java.util.List;

public interface PostService {
    Post addPost(Post post);

    void deletePost(Long id);

    Post updatePost(Post post, Long userId);

    Post getById(Long id);

    List<Post> getAll();
}
