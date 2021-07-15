package com.rest.app.orionrestapplication.rest;

import com.rest.app.orionrestapplication.annotation.Monitor;
import com.rest.app.orionrestapplication.dto.MessageDto;
import com.rest.app.orionrestapplication.dto.PostDto;
import com.rest.app.orionrestapplication.model.MonitorType;
import com.rest.app.orionrestapplication.model.Post;
import com.rest.app.orionrestapplication.service.PostService;
import com.rest.app.orionrestapplication.service.UserService;
import com.rest.app.orionrestapplication.util.UserDetailUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/post/")
@Log4j
public class PostController {
    private static final String URL_VALUE = "/post/";

    private final PostService postService;
    private final UserService userService;
    private final UserDetailUtil userDetails;

    @Autowired
    public PostController(PostService postService, UserService userService, UserDetailUtil userDetails) {
        this.postService = postService;
        this.userService = userService;
        this.userDetails = userDetails;
    }

    @PostMapping(value = "new")
    @Monitor(requestName = MonitorType.POST)
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto) {
        log.info("Called [" + URL_VALUE + "new]" + "PostDto: " + postDto.toString());
        var user = userService.findByUserName(userDetails.getUser().getUsername());

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
        log.info("Called [" + URL_VALUE + id + "]");
        try {
            var post = postService.getById(id);

            if (post == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            var result = PostDto.fromPost(post);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            log.warn("Post with id=" + id + " not found");
            return new ResponseEntity<>(new PostDto(null, null, null, "Not found"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = {"update/{id}"})
    @Monitor(requestName = MonitorType.PUT)
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long id, @RequestBody PostDto postDto) {
        log.info("Called [" + URL_VALUE + "update/" + id + "] PostDto: " + postDto.toString());
        try {
            var post = postService.getById(id);
            postDto.setId(post.getId());

            var user = userService.findByUserName(userDetails.getUser().getUsername());
            log.info("Get user by username=" + userDetails.getUser().getUsername());

            var newPost = PostDto.toPost(postDto, user);
            var result = postService.updatePost(newPost, user.getId());
            var response = PostDto.fromPost(result);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Post with id=" + id + " not found");
            return new ResponseEntity<>(new PostDto(null, null, null, "Not found"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "delete/{id}")
    @Monitor(requestName = MonitorType.DELETE)
    public ResponseEntity<MessageDto> deletePostById(@PathVariable(name = "id") Long id) {
        var message = new MessageDto();
        try {
            log.info("Called [" + URL_VALUE + "delete/" + id + "]");
            var post = postService.getById(id);

            if (!userDetails.getUser().getUsername().equals(post.getAuthor().getUsername())) {
                log.warn("Post with user username=" + userDetails.getUser().getUsername() + " not found");
                message.setMessage("Not a author");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

            postService.deletePost(post.getId());
            String mes = "Post with id=" + post.getId() + " deleted";
            message.setMessage(mes);
            log.info(mes);

            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.warn("Post with id=" + id + " not found");
            message.setMessage("Post with id=" + id + " Not Found");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}
