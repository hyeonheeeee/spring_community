package com.community.dto;

import com.community.model.Posts;
import com.community.model.Users;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostDto {
    private int id;
    private String title;
    private String detail;
    private MultipartFile post_image;
    private int likes;
    private int hits;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String user_email;

    public Posts toEntity(Users users, String imagePath) {
        return Posts.builder()
                .title(this.title)
                .detail(this.detail)
                .post_image(imagePath)
                .likes(this.likes)
                .hits(this.hits)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .users(users)
                .build();
    }

    public Posts toEntity(Users users, Posts post, String imagePath) {
        post.updatePost(this.title, this.detail, imagePath);
        return post;
    }
}
