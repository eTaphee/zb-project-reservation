package com.zeroboase.reservation.domain.member.controller;

import com.zeroboase.reservation.configuration.security.TokenProvider;
import com.zeroboase.reservation.domain.member.dto.Login;
import com.zeroboase.reservation.domain.member.dto.model.MemberDto;
import com.zeroboase.reservation.domain.member.service.MemberService;
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
    public ResponseEntity<Login.Response> login(
        @Valid @RequestBody Login.Request request) {
        MemberDto member = memberService.authenticate(request);
        return ResponseEntity.ok(
            Login.Response.from(
                tokenProvider.generateToken(member.username(), member.roles())
            )
        );
    }
}
