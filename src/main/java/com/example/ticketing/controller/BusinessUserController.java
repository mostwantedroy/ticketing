package com.example.ticketing.controller;

import com.example.ticketing.common.ResponseData;
import com.example.ticketing.domain.BusinessUser;
import com.example.ticketing.service.businessuser.BusinessUserService;
import com.example.ticketing.service.businessuser.dto.BusinessUserDto;
import com.example.ticketing.service.businessuser.dto.BusinessUserLoginRequest;
import com.example.ticketing.service.businessuser.dto.BusinessUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/business-user")
@RequiredArgsConstructor
public class BusinessUserController {
    private final BusinessUserService businessUserService;

    @PostMapping("/sign-up")
    public ResponseData<BusinessUserDto> signUpBusinessUser(@RequestBody @Valid BusinessUserRequest businessUserRequest) {
        BusinessUser businessUser = businessUserService.registerBusinessUser(businessUserRequest);

        return ResponseData.success(BusinessUserDto.fromDomain(businessUser));
    }

    @PostMapping("/login")
    public ResponseData<String> loginBusinessUser(@RequestBody BusinessUserLoginRequest businessUserLoginRequest) {
        String businessUserToken = businessUserService.loginBusinessUser(businessUserLoginRequest);

        return ResponseData.success(businessUserToken);
    }

    @GetMapping("/{businessUserId}")
    public ResponseData<BusinessUserDto> getBusinessUser(@PathVariable("businessUserId") Long businessUserId) {
        BusinessUser businessUser = businessUserService.getBusinessUser(businessUserId);

        return ResponseData.success(BusinessUserDto.fromDomain(businessUser));
    }

}
