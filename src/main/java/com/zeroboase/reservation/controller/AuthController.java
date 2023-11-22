package com.zeroboase.reservation.controller;

import com.zeroboase.reservation.configuration.security.TokenProvider;
import com.zeroboase.reservation.dto.MemberDto;
import com.zeroboase.reservation.dto.request.LoginRequestDto;
import com.zeroboase.reservation.dto.response.LoginResponseDto;
import com.zeroboase.reservation.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(
        @Valid @RequestBody LoginRequestDto request) {
        MemberDto member = memberService.authenticate(request);
        return ResponseEntity.ok(
            LoginResponseDto.from(
                tokenProvider.generateToken(member.username(), member.roles())
            )
        );
    }
}
