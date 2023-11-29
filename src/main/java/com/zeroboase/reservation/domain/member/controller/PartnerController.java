package com.zeroboase.reservation.domain.member.controller;

import static com.zeroboase.reservation.domain.member.entity.type.Role.PARTNER;
import static org.springframework.http.HttpStatus.OK;

import com.zeroboase.reservation.domain.member.dto.RegisterMember;
import com.zeroboase.reservation.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 파트너 controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/partners")
public class PartnerController {

    private final MemberService memberService;

    @PostMapping("register")
    public ResponseEntity<Void> registerPartner(
        @Valid @RequestBody RegisterMember.Request request) {
        memberService.registerMember(request, PARTNER);
        return new ResponseEntity<>(OK);
    }
}
