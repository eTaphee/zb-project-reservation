package com.zeroboase.reservation.service;

import static com.zeroboase.reservation.exception.ErrorCode.AUTHENTICATE_FAIL;
import static com.zeroboase.reservation.exception.ErrorCode.USERNAME_ALREADY_EXISTS;
import static com.zeroboase.reservation.exception.ErrorCode.USERNAME_NOT_FOUND;

import com.zeroboase.reservation.domain.Member;
import com.zeroboase.reservation.dto.MemberDto;
import com.zeroboase.reservation.dto.request.LoginRequestDto;
import com.zeroboase.reservation.dto.request.RegisterMemberRequestDto;
import com.zeroboase.reservation.exception.ReservationException;
import com.zeroboase.reservation.repository.MemberRepository;
import com.zeroboase.reservation.type.Role;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
            .orElseThrow(() -> new ReservationException(USERNAME_NOT_FOUND));
    }

    /**
     * 회원 가입
     * <p>
     * 점장 및 사용자의 정보가 다르지 않기 때문에 하나의 서비스 함수에서 처리한다.
     *
     * @param request 회원 가입 요청
     * @param role    역할(PARTNER, CUSTOMER)
     */
    @Transactional
    @Override
    public MemberDto registerMember(RegisterMemberRequestDto request, Role role) {
        if (memberRepository.existsByUsername(request.username())) {
            throw new ReservationException(USERNAME_ALREADY_EXISTS);
        }

        Member member = memberRepository.save(
            Member.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(Collections.singletonList(role))
                .build());

        return MemberDto.fromEntity(member);
    }

    /**
     * 로그인 인증
     *
     * @param request 로그인 요청
     * @return 회원 정보
     */
    @Transactional(readOnly = true)
    @Override
    public MemberDto authenticate(LoginRequestDto request) {
        Member member = memberRepository.findByUsername(request.username())
            .orElseThrow(() -> new ReservationException(AUTHENTICATE_FAIL));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new ReservationException(AUTHENTICATE_FAIL);
        }

        return MemberDto.fromEntity(member);
    }
}
