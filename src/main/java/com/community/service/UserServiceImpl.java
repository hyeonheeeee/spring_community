package com.community.service;

import com.community.dao.UserDao;
import com.community.dto.CustomUserDetails;
import com.community.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDto userDto, MultipartFile file) throws IOException, SQLException {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
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

//    @Override
//    public UserDto login(UserDto userDto) {
//        UserDto user = userDao.findByEmail(userDto.getEmail());
//        if (user != null && bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword())) {
//            return user;
//        }
//
//        return null ;
//    }

    @Override
    public UserDetails login(UserDto userDto) {
        UserDto user = userDao.findByEmail(userDto.getEmail());
        if (user != null && passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return new CustomUserDetails(user);
        }

        return null ;
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
