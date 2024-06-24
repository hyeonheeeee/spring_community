package com.community.dao;

import com.community.dto.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUser(UserDto userDto) throws SQLException {
        String sql = "INSERT INTO users (email, password, nickname, user_image, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, userDto.getEmail(), userDto.getPassword(), userDto.getNickname(),
                            userDto.getUser_image(), userDto.getCreated_at(), userDto.getUpdated_at());
    }
}
