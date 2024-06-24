package com.community.controller;

import com.community.dto.CommentDto;
import com.community.dto.PostDto;
import com.community.model.Comments;
import com.community.model.Posts;
import com.community.response.Response;
import com.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                                        @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        postDto.setUser_id(1);
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
                                        @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        postDto.setUser_id(1);
        postDto.setId(post_id);
        postService.updatePost(postDto, file);
        return Response.createResponse(HttpStatus.OK, "update_post_success", post_id);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable int post_id) {
        postService.deletePost(post_id);
        return Response.createResponse(HttpStatus.OK, "delete_post_success", null);
    }

    @GetMapping("/{post_id}/comments")
    public ResponseEntity<?> getPostComments(@PathVariable int post_id) {
        List<Comments> comments = postService.getPostComments(post_id);
        return Response.createResponse(HttpStatus.OK, null, comments);
    }

    @PostMapping("/{post_id}/comments")
    public ResponseEntity<?> createComment(@PathVariable int post_id, @RequestPart("comment") CommentDto commentDto) {
        commentDto.setPost_id(post_id);
        commentDto.setUser_id(1);
        postService.createComment(commentDto);
        return Response.createResponse(HttpStatus.CREATED, "write_comment_success", null);
    }

    @PutMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<?> updateComment(@PathVariable int post_id, @PathVariable int comment_id, @RequestPart("comment") CommentDto commentDto) {
        commentDto.setPost_id(post_id);
        commentDto.setId(comment_id);
        commentDto.setUser_id(1);
        postService.updateComment(commentDto);
        return Response.createResponse(HttpStatus.OK, "update_comment_success", null);
    }

    @DeleteMapping("/{post_id}/comments/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable int post_id, @PathVariable int comment_id) {
        postService.deleteComment(post_id, comment_id);
        return Response.createResponse(HttpStatus.OK, "delete_comment_success", null);
    }



}
