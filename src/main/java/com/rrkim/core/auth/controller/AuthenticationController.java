package com.rrkim.core.auth.controller;

import com.rrkim.core.auth.domain.Eula;
import com.rrkim.core.auth.dto.data.EulaDto;
import com.rrkim.core.auth.dto.data.TokenDto;
import com.rrkim.core.auth.dto.data.TokenValidationDto;
import com.rrkim.core.auth.dto.request.CheckIdRequestDto;
import com.rrkim.core.auth.dto.request.UpdateUserInfoRequestDto;
import com.rrkim.core.auth.service.EulaService;
import com.rrkim.core.auth.util.AuthUtility;
import com.rrkim.core.common.dto.ApiResponse;
import com.rrkim.core.auth.dto.data.UserDto;
import com.rrkim.core.auth.dto.request.SignUpRequestDto;
import com.rrkim.core.auth.service.JwtAuthenticationService;
import com.rrkim.core.auth.service.UserService;
import com.rrkim.core.auth.dto.request.SignInRequestDto;
import com.rrkim.core.common.util.HashMapCreator;
import com.rrkim.core.common.util.ApiUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "인증 API")
public class AuthenticationController {

    private final JwtAuthenticationService jwtAuthenticationService;
    private final UserService userService;
    private final EulaService eulaService;

    @Operation(summary = "토큰 발급", description = "회원에 대한 토큰을 발급합니다")
    @PostMapping(value="/auth/token")
    public @ResponseBody ApiResponse createToken(@RequestBody @Valid SignInRequestDto signInRequestDto) {
        TokenDto tokenDto = jwtAuthenticationService.createToken(signInRequestDto);

        return ApiUtility.createResponse(true, tokenDto);
    }

    @Operation(summary = "토큰 검증", description = "회원에 대한 토큰을 검증합니다")
    @GetMapping(value="/auth/token")
    public @ResponseBody ApiResponse validateToken(HttpServletRequest httpServletRequest) {
        String token = AuthUtility.resolveToken(httpServletRequest);
        TokenValidationDto tokenValidationDto = jwtAuthenticationService.validateToken(token, null);

        return ApiUtility.createResponse(true, tokenValidationDto);
    }

    @Operation(summary = "회원 ID 조회", description = "등록된 회원 ID가 있는지 조회합니다.")
    @GetMapping(value="/auth/check-user")
    public @ResponseBody ApiResponse signUpProcess(@Valid CheckIdRequestDto checkIdRequestDto) {
        boolean checkId = userService.checkIdProcess(checkIdRequestDto);

        return ApiUtility.createResponse(true, HashMapCreator.builder().put("checkId", checkId).build());
    }

    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다.")
    @PostMapping(value="/auth/sign-up")
    public @ResponseBody ApiResponse signUpProcess(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        UserDto userDto = userService.signUpProcess(signUpRequestDto);

        return ApiUtility.createResponse(true, HashMapCreator.builder().put("userId", userDto.getUserId()).build());
    }

    @Operation(summary = "약관 조회", description = "약관을 조회합니다.")
    @GetMapping(value="/auth/eula")
    public @ResponseBody ApiResponse selectEula() {
        List<EulaDto> eulaDtos = eulaService.selectEulaList();

        return ApiUtility.createResponse(true, eulaDtos, eulaDtos.size());
    }

}