package com.zeroboase.reservation.controller;

import static com.zeroboase.reservation.type.Role.PARTNER;
import static org.springframework.http.HttpStatus.OK;

import com.zeroboase.reservation.dto.request.RegisterMemberRequestDto;
import com.zeroboase.reservation.service.MemberService;
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
        @Valid @RequestBody RegisterMemberRequestDto request) {
        memberService.registerMember(request, PARTNER);
        return new ResponseEntity<>(OK);
    }
}
