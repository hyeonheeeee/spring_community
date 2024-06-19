package com.community.service;

import com.community.dto.PostDto;
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

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Posts> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void createPost(PostDto postDto, MultipartFile file) {
        Posts posts = convertToEntity(postDto, file);
        postRepository.save(posts);
    }

    @Override
    public Optional<Posts> getPostById(int postId) {
        return postRepository.findById((long)postId);
    }

    @Override
    public void updatePost(int postId, PostDto postDto, MultipartFile file) {
        Posts posts = postRepository.findById((long) postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        updateEntity(posts, postDto, file);
        postRepository.save(posts);
    }

    @Override
    public void deletePost(int postId) {
        postRepository.deleteById((long) postId);
    }

    private Posts convertToEntity(PostDto postDto, MultipartFile file) {
        Posts posts = new Posts();
        posts.setTitle(postDto.getTitle());
        posts.setDetail(postDto.getDetail());
        posts.setLikes(postDto.getLikes());
        posts.setHits(postDto.getHits());
        posts.setCreated_at(postDto.getCreated_at());
        posts.setUpdated_at(postDto.getUpdated_at());

        //이미지 저장로직
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String uploadDir = new File("src/main/resources/static/images/").getAbsolutePath();
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                file.transferTo(new File(uploadDir + File.separator + fileName));
                posts.setPost_image(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Users user = userRepository.findById(postDto.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));
        posts.setUsers(user);

        return posts;
    }

    private void updateEntity(Posts posts, PostDto postDto, MultipartFile file) {
        posts.setTitle(postDto.getTitle());
        posts.setDetail(postDto.getDetail());
        posts.setLikes(postDto.getLikes());
        posts.setHits(postDto.getHits());
        posts.setUpdated_at(postDto.getUpdated_at()); // updated_at만 업데이트

        // 이미지 저장로직
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String uploadDir = new File("src/main/resources/static/images/").getAbsolutePath();
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                file.transferTo(new File(uploadDir + File.separator + fileName));
                posts.setPost_image(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Users user = userRepository.findById(postDto.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));
        posts.setUsers(user);
    }

}