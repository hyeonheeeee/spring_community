package com.community.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "detail")
    private String detail;

    @Column(name = "post_image")
    private String post_image;

    @Column(name = "likes")
    private int likes;

    @Column(name = "hits")
    private int hits;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users users;

    @Builder
    public Posts(int id, String title, String detail, String post_image, int likes, LocalDateTime created_at, int hits, LocalDateTime updated_at, Users users) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.post_image = post_image;
        this.likes = likes;
        this.created_at = created_at;
        this.hits = hits;
        this.updated_at = updated_at;
        this.users = users;
    }

    public void updatePost(String title, String detail, String post_image) {
        this.title = title;
        this.detail = detail;
        this.post_image = post_image;
        this.updated_at = LocalDateTime.now();
    }
}

