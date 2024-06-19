package com.community.controller;

import com.community.dto.PostDto;
import com.community.model.Posts;
import com.community.response.Response;
import com.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        List<Posts> posts = postService.getAllPosts();
        return Response.createResponse(HttpStatus.OK, null, posts);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestPart("post") PostDto postDto,
                             @RequestPart(value = "file", required = false) MultipartFile file) {
        postService.createPost(postDto, file);
        return Response.createResponse(HttpStatus.CREATED, "write_post_success", null);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<?> getPostById(@PathVariable int post_id) {
        Optional<Posts> post = postService.getPostById(post_id);
        return Response.createResponse(HttpStatus.OK, null, post);
    }

    @PutMapping("/{post_id}")
    public ResponseEntity<?> updatePost(@PathVariable int post_id,
                             @RequestPart("post") PostDto postDto,
                             @RequestPart(value = "file", required = false) MultipartFile file) {
        postService.updatePost(post_id, postDto, file);
        return Response.createResponse(HttpStatus.OK, "update_post_success", post_id);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable int post_id) {
        postService.deletePost(post_id);
        return Response.createResponse(HttpStatus.OK, "delete_post_success", null);
    }

}
