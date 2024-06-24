package com.community.service;

import com.community.dto.CommentDto;
import com.community.dto.PostDto;
import com.community.model.Comments;
import com.community.model.Posts;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Posts> getAllPosts();

    void createPost(PostDto postDto, MultipartFile file) throws IOException;

    Optional<Posts> getPostById(int postId);

    void updatePost(PostDto postDto, MultipartFile file) throws IOException;

    void deletePost(int postId);

    List<Comments> getPostComments(int postId);

    void createComment(CommentDto commentDto);

    void updateComment(CommentDto commentDto);

    void deleteComment(int postId, int commentId);
}
