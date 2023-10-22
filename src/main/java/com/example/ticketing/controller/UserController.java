package com.example.ticketing.controller;

import com.example.ticketing.common.ResponseData;
import com.example.ticketing.domain.User;
import com.example.ticketing.service.user.UserService;
import com.example.ticketing.service.user.dto.UserDto;
import com.example.ticketing.service.user.dto.UserLoginRequest;
import com.example.ticketing.service.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseData<UserDto> signUpUser(@RequestBody @Valid UserRequest userRequest) {
        User user = userService.registerUser(userRequest);

        return ResponseData.success(UserDto.fromDomain(user));
    }

    @PostMapping("/login")
    public ResponseData<String> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        String userToken = userService.loginUser(userLoginRequest);

        return ResponseData.success(userToken);
    }

    @GetMapping("/{userId}")
    public ResponseData<UserDto> getUser(@PathVariable("userId") Long userId) {
        User user = userService.getUser(userId);

        return ResponseData.success(UserDto.fromDomain(user));
    }
}
