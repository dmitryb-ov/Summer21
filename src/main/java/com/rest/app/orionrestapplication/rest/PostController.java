package com.rest.app.orionrestapplication.rest;

import com.rest.app.orionrestapplication.annotation.Monitor;
import com.rest.app.orionrestapplication.dto.PostDto;
import com.rest.app.orionrestapplication.model.MonitorType;
import com.rest.app.orionrestapplication.model.Post;
import com.rest.app.orionrestapplication.service.PostService;
import com.rest.app.orionrestapplication.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/post/")
@Log4j
public class PostController {
    private static final String URL_VALUE = "/post/";

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(value = "new")
    @Monitor(requestName = MonitorType.POST)
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto) {
        log.info("Called [" + URL_VALUE + "new " + "PostDto: " + postDto.toString());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (UserDetails) authentication.getPrincipal();
        var user = userService.findByUserName(userDetails.getUsername());

        var post = new Post();
        post.setAuthor(user);
        post.setText(postDto.getText());

        var responsePost = postService.addPost(post);

        var response = PostDto.fromPost(responsePost);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{id}")
    @Monitor(requestName = MonitorType.GET)
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        log.info("Called [" + URL_VALUE + id);
        var post = postService.getById(id);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        var result = PostDto.fromPost(post);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = {"update/{id}"})
    @Monitor(requestName = MonitorType.PUT)
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long id, @RequestBody PostDto postDto) {
        log.info("Called [" + URL_VALUE + "update/" + id + " PostDto: " + postDto.toString());
        var post = postService.getById(id);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        postDto.setId(post.getId());

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (UserDetails) authentication.getPrincipal();

        var user = userService.findByUserName(userDetails.getUsername());
        log.info("Get user by username=" + userDetails.getUsername());

        var newPost = PostDto.toPost(postDto, user);

        var result = postService.updatePost(newPost, user.getId());

        var response = PostDto.fromPost(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
