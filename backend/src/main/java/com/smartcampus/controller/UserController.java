package com.smartcampus.controller;

import com.smartcampus.dto.ApiResponse;
import com.smartcampus.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ApiResponse<?> getProfile(Principal principal) {
        var user = userService.getUserByUsername(principal.getName());
        Map<String, Object> profile = new HashMap<>();
        profile.put("username", user.getUsername());
        profile.put("realName", user.getRealName());
        profile.put("studentId", user.getStudentId());
        profile.put("email", user.getEmail());
        profile.put("phone", user.getPhone());
        profile.put("creditScore", user.getCreditScore());
        profile.put("role", user.getRole());
        return ApiResponse.success(profile);
    }
}
