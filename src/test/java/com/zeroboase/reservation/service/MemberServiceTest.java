package com.zeroboase.reservation.service;

import static com.zeroboase.reservation.domain.member.entity.type.Role.CUSTOMER;
import static com.zeroboase.reservation.exception.ErrorCode.AUTHENTICATE_FAIL;
import static com.zeroboase.reservation.exception.ErrorCode.USERNAME_ALREADY_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.zeroboase.reservation.domain.member.dto.Login;
import com.zeroboase.reservation.domain.member.dto.RegisterMember;
import com.zeroboase.reservation.domain.member.dto.model.MemberDto;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.member.entity.type.Role;
import com.zeroboase.reservation.domain.member.repository.MemberRepository;
import com.zeroboase.reservation.domain.member.service.impl.MemberServiceImpl;
import com.zeroboase.reservation.exception.ReservationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    private static final String username = "username";
    private static final String password = "123123";
    private static final List<Role> roles = Collections.singletonList(CUSTOMER);

    private static final Login.Request loginRequest = Login.Request.builder()
        .username(username)
        .password(password)
        .build();

    private static final RegisterMember.Request registerMemberRequest = RegisterMember.Request.builder()
        .username(username)
        .password(password)
        .build();

    @Test
    @DisplayName("회원 가입 성공")
    void successRegisterMember() {
        // given
        given(memberRepository.existsByUsername(username)).willReturn(false);

        given(memberRepository.save(any()))
            .willReturn(Member.builder()
                .username(username)
                .roles(roles)
                .build());

        // when
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

        MemberDto member = memberService.registerMember(registerMemberRequest, CUSTOMER);

        // then
        verify(memberRepository, times(1)).existsByUsername(username);
        verify(memberRepository, times(1)).save(captor.capture());
        assertEquals(username, member.username());
        assertEquals(roles, member.roles());
    }

    @Test
    @DisplayName("회원 가입 실패 - 아이디 중복")
    void failRegisterMember_USERNAME_ALREADY_EXISTS() {
        // given
        given(memberRepository.existsByUsername(username)).willReturn(true);

        // when
        ReservationException exception = assertThrows(ReservationException.class,
            () -> memberService.registerMember(registerMemberRequest, CUSTOMER));

        // then
        assertEquals(USERNAME_ALREADY_EXISTS, exception.getErrorCode());
    }

    @Test
    @DisplayName("로그인 성공")
    void successAuthenticate() {
        // given
        given(memberRepository.findByUsername(username))
            .willReturn(
                Optional.of(
                    Member.builder()
                        .username(username)
                        .password(password)
                        .roles(roles)
                        .build()));

        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when
        MemberDto member = memberService.authenticate(loginRequest);

        // then
        assertEquals(username, member.username());
        assertEquals(roles, member.roles());
    }

    @Test
    @DisplayName("로그인 실패 - 사용자 없음")
    void failAuthenticate_username_AUTHENTICATE_FAIL() {
        // given
        given(memberRepository.findByUsername(username)).willReturn(Optional.empty());

        // when
        ReservationException exception = assertThrows(ReservationException.class,
            () -> memberService.authenticate(loginRequest));

        // then
        assertEquals(AUTHENTICATE_FAIL, exception.getErrorCode());
    }

    @Test
    @DisplayName("로그인 실패 - 패스워드 불일치")
    void failAuthenticate_password_AUTHENTICATE_FAIL() {
        // given
        given(memberRepository.findByUsername(username))
            .willReturn(Optional.of(Member.builder().build()));

        // when
        ReservationException exception = assertThrows(ReservationException.class,
            () -> memberService.authenticate(loginRequest));

        // then
        assertEquals(AUTHENTICATE_FAIL, exception.getErrorCode());
    }
}