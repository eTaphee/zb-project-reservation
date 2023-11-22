package com.zeroboase.reservation.service;

import com.zeroboase.reservation.dto.MemberDto;
import com.zeroboase.reservation.dto.request.LoginRequestDto;
import com.zeroboase.reservation.dto.request.RegisterMemberRequestDto;
import com.zeroboase.reservation.type.Role;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface MemberService extends UserDetailsService {

    MemberDto registerMember(RegisterMemberRequestDto request, Role role);

    MemberDto authenticate(LoginRequestDto request);
}
