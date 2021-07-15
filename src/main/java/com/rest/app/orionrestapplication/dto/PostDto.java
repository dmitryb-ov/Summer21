package com.rest.app.orionrestapplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.app.orionrestapplication.model.Post;
import com.rest.app.orionrestapplication.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Log4j
public class PostDto extends BaseDto {
    private String username;
    private String email;
    private String text;

    public PostDto(Long id, String username, String email, String text) {
        super(id);
        this.username = username;
        this.email = email;
        this.text = text;
    }

    public static Post toPost(PostDto postDto, User user) {
        var post = new Post();
        post.setId(postDto.getId());
        post.setAuthor(user);
        post.setText(postDto.getText());

        log.info("Convert postDto to Post");
        return post;
    }

    public static PostDto fromPost(Post post) {
        var postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setUsername(post.getAuthor().getUsername());
        postDto.setEmail(post.getAuthor().getEmail());
        postDto.setText(post.getText());

        log.info("Convert post to postDto");
        return postDto;
    }
}
