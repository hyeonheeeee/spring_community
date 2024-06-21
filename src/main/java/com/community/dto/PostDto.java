package com.community.dto;

import com.community.model.Posts;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private int id;
    private String title;
    private String detail;
    private MultipartFile post_image;
    private int likes;
    private int hits;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int user_id;


}
