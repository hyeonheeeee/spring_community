package com.community.service;

import com.community.dto.CommentDto;
import com.community.dto.PostDto;
import com.community.model.Comments;
import com.community.repository.CommentRepository;
import com.community.repository.PostRepository;
import com.community.repository.UserRepository;
import com.community.model.Posts;
import com.community.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Posts> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void createPost(PostDto postDto, MultipartFile file) throws IOException {
        Users users = userRepository.findByEmail(postDto.getUser_email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String imagePath = null;
        if (file != null && !file.isEmpty()) {
            imagePath = saveImage(file);
        }

        Posts post = postDto.toEntity(users, imagePath);
        postRepository.save(post);
    }

    @Override
    public Optional<Posts> getPostById(int postId) {
        return postRepository.findById(postId);
    }

    @Override
    public void updatePost(PostDto postDto, MultipartFile file) throws IOException {
        Posts posts = postRepository.findById(postDto.getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Users users = userRepository.findByEmail(postDto.getUser_email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String imagePath = posts.getPost_image();
        if (imagePath != null && !imagePath.isEmpty()) {
            imagePath = saveImage(file);
        }

        postDto.toEntity(users, posts, imagePath);
        postRepository.save(posts);
    }

    @Override
    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<Comments> getPostComments(int postId) {
        return commentRepository.findByPostsId(postId);
    }

    @Override
    public void createComment(CommentDto commentDto) {
        Posts posts = postRepository.findById(commentDto.getPost_id())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        //유저 수정해야함
        Users users = userRepository.findById(commentDto.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comments comments = commentDto.toEntity(posts, users);
        commentRepository.save(comments);
    }

    @Override
    public void updateComment(CommentDto commentDto) {
        Posts posts = postRepository.findById(commentDto.getPost_id())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        //유저 수정해야함
        Users users = userRepository.findById(commentDto.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comments comments = commentRepository.findById(commentDto.getId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Comments updateComment = commentDto.toEntity(posts, users, comments);
        commentRepository.save(updateComment);
    }

    @Override
    public void deleteComment(int postId, int commentId) {
        commentRepository.deleteById(commentId);
    }

    public String saveImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        String uploadDir = new File("src/main/resources/static/images/").getAbsolutePath();
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
        file.transferTo(new File(uploadDir + File.separator + fileName));
        return fileName;
    }

}
