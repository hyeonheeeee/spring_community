package com.community.controller;

import com.community.dto.CustomUserDetails;
import com.community.dto.UserDto;
import com.community.response.Response;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestPart("user") UserDto userDto,
                                     @RequestPart(value = "file", required = false) MultipartFile file) throws SQLException, IOException {
        userService.createUser(userDto, file);
        return Response.createResponse(HttpStatus.CREATED, "register_success", null);
    }

//    @GetMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
//        UserDto userInfo = userService.login(userDto);
//        return Response.createResponse(HttpStatus.OK, "login_success", userInfo);
//    }

//    @GetMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
//        UserDetails userInfo = userService.login(userDto);
//        return Response.createResponse(HttpStatus.OK, "login_success", userInfo);
//    }
}
