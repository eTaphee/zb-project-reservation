package com.zeroboase.reservation.domain.member.service;

import com.zeroboase.reservation.domain.member.dto.Login;
import com.zeroboase.reservation.domain.member.dto.RegisterMember;
import com.zeroboase.reservation.domain.member.dto.model.MemberDto;
import com.zeroboase.reservation.domain.member.entity.type.Role;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface MemberService extends UserDetailsService {

    MemberDto registerMember(RegisterMember.Request request, Role role);

    MemberDto authenticate(Login.Request request);
}
