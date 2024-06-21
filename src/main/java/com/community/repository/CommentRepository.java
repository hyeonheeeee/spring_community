package com.community.repository;

import com.community.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer> {
    // JPA는 메서드 이름을 분석하여 쿼리를 생성!
    List<Comments> findByPostsId(int postId);
}
