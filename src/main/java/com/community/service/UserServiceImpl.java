package com.community.service;

import com.community.dao.UserDao;
import com.community.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void createUser(UserDto userDto, MultipartFile file) throws IOException, SQLException {
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        String imagePath = null;
        if (file != null && !file.isEmpty()) {
            imagePath = saveImage(file);
        }
        userDto.setUser_image(imagePath);

        userDto.setCreated_at(LocalDateTime.now());
        userDto.setUpdated_at(LocalDateTime.now());

        userDao.insertUser(userDto);
    }

    public String saveImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        String uploadDir = new File("src/main/resources/static/images/").getAbsolutePath();
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
        file.transferTo(new File(uploadDir + File.separator + fileName));
        return fileName;
    }
}
