package com.community.dto;

import com.community.model.Comments;
import com.community.model.Posts;
import com.community.model.Users;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {
    private int id;
    private int user_id;
    private int post_id;
    private String detail;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Comments toEntity(Posts posts, Users users) {
        return Comments.builder()
                .detail(this.detail)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .users(users)
                .posts(posts)
                .build();
    }

    public Comments toEntity(Posts posts, Users users, Comments comment) {
        comment.updateDetail(this.detail);
        return comment;
    }

}
