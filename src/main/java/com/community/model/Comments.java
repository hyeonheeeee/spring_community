package com.community.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "detail")
    private String detail;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "user_email")
    @JsonBackReference
    private Users users;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Posts posts;

    @Builder
    public Comments(int id, String detail, LocalDateTime created_at, LocalDateTime updated_at, Users users, Posts posts) {
        this.id = id;
        this.detail = detail;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.users = users;
        this.posts = posts;
    }

    public void updateDetail(String detail) {
        this.detail = detail;
        this.updated_at = LocalDateTime.now();
    }

}
