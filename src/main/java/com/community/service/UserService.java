package com.community.service;

import com.community.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface UserService {
    void createUser(UserDto userDto, MultipartFile file) throws IOException, SQLException;
}
