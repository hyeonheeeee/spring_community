package com.community.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenDto {
    private int id;
    private String user_email;
    private String refresh;
    private LocalDateTime expired_at;
}
