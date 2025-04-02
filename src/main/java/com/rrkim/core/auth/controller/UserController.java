package com.rrkim.core.auth.controller;

import com.rrkim.core.auth.dto.data.EulaDto;
import com.rrkim.core.auth.dto.data.TokenDto;
import com.rrkim.core.auth.dto.data.TokenValidationDto;
import com.rrkim.core.auth.dto.data.UserDto;
import com.rrkim.core.auth.dto.request.CheckIdRequestDto;
import com.rrkim.core.auth.dto.request.SignInRequestDto;
import com.rrkim.core.auth.dto.request.SignUpRequestDto;
import com.rrkim.core.auth.dto.request.UpdateUserInfoRequestDto;
import com.rrkim.core.auth.service.EulaService;
import com.rrkim.core.auth.service.JwtAuthenticationService;
import com.rrkim.core.auth.service.UserService;
import com.rrkim.core.auth.util.AuthUtility;
import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.common.util.ApiUtility;
import com.rrkim.core.common.util.HashMapCreator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "회원 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "개인정보 조회", description = "회원의 정보를 조회합니다.")
    @GetMapping(value="/user")
    public @ResponseBody ApiResponse updateUserInfo() {
        UserDto userDto = userService.getUserInfo();

        return ApiUtility.createResponse(true, userDto);
    }

    @Operation(summary = "개인정보 수정", description = "회원의 정보를 수정합니다.")
    @PutMapping(value="/user")
    public @ResponseBody ApiResponse updateUserInfo(@RequestBody UpdateUserInfoRequestDto updateUserInfoRequestDto) {
        UserDto userDto = userService.updateUserInfo(updateUserInfoRequestDto);

        return ApiUtility.createResponse(true, userDto);
    }

}