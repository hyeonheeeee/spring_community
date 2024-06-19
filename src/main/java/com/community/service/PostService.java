package com.community.service;

import com.community.dto.PostDto;
import com.community.model.Posts;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Posts> getAllPosts();
    void createPost(PostDto postDto, MultipartFile file);
    Optional<Posts> getPostById(int postId);
    void updatePost(int postId, PostDto postDto, MultipartFile file);
    void deletePost(int postId);
}
