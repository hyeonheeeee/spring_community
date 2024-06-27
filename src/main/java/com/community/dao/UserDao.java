package com.community.dao;

import com.community.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUser(UserDto userDto) {
        String sql = "INSERT INTO users (email, password, nickname, user_image, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, userDto.getEmail(), userDto.getPassword(), userDto.getNickname(), userDto.getUser_image(), userDto.getCreated_at(), userDto.getUpdated_at());
    }

    public UserDto findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserRowMapper());
    }

    private static class UserRowMapper implements RowMapper<UserDto> {
        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDto user = new UserDto();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setNickname(rs.getString("nickname"));
            user.setUser_image(rs.getString("user_image"));
            user.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
            user.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
            return user;
        }
    }
}
