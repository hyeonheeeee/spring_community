package com.community.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String email;
    private String password;
    private String nickname;
    private String user_image;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
