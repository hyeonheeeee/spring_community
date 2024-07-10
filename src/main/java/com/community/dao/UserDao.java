package com.community.dao;

import com.community.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

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

    public void addRefreshToken(String username, String refresh, long expired_at) {
        String sql = "INSERT INTO sessions(user_email, refresh, expired_at) VALUES (?, ?, ?)";
        Timestamp expiredAt = new Timestamp(System.currentTimeMillis() + expired_at);
        jdbcTemplate.update(sql, username, refresh, expiredAt);
    }

    public boolean existsByRefresh(String userEmail, String refresh) {
        String sql = "SELECT COUNT(*) FROM sessions WHERE user_email = ? AND refresh = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userEmail, refresh}, Integer.class);
        return count != null && count > 0;
    }

    public void deleteByRefresh(String userEmail, String refresh) {
        String sql = "DELETE FROM sessions WHERE user_email = ? AND refresh = ?";
        jdbcTemplate.update(sql, userEmail, refresh);
    }

    private static class UserRowMapper implements RowMapper<UserDto> {
        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDto user = new UserDto();
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
