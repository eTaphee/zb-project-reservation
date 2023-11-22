package com.zeroboase.reservation.controller;

import static com.zeroboase.reservation.type.Role.CUSTOMER;
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
 * 고객 controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final MemberService memberService;

    @PostMapping("register")
    public ResponseEntity<Void> registerCustomer(
        @Valid @RequestBody RegisterMemberRequestDto request) {
        memberService.registerMember(request, CUSTOMER);
        return new ResponseEntity<>(OK);
    }
}
