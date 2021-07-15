package com.rest.app.orionrestapplication.service.impl;

import com.rest.app.orionrestapplication.model.Post;
import com.rest.app.orionrestapplication.repository.PostRepository;
import com.rest.app.orionrestapplication.repository.UserRepository;
import com.rest.app.orionrestapplication.service.PostService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Log4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Post addPost(Post post) {
        var postResponse = postRepository.save(post);
        log.info("Post added. ID: " + postResponse.getId());
        return postResponse;
    }

    @Override
    public void deletePost(Long id) {
        log.info("Delete post with id=" + id);
        postRepository.deleteById(id);
    }

    @Override
    @Modifying(clearAutomatically = true)
    @Transactional
    public Post updatePost(Post post, Long userId) throws EntityNotFoundException {
        post.setAuthor(userRepository.getById(userId));
        var postResponse = postRepository.save(post);
        log.info("Updated post. PostId: " + post.getId() + " UserId: " + userId);
        return postResponse;
    }

    @Override
    public Post getById(Long id) {
        var postResponse = postRepository.getById(id);
        log.info("Get post by id=" + id);
        return postResponse;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = postRepository.findAll();
        log.info("Get all post. Count rows=" + posts.size());
        return posts;
    }
}
