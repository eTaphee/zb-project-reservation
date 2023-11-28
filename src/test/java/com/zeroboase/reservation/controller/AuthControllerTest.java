package com.zeroboase.reservation.controller;

import static com.zeroboase.reservation.exception.ErrorCode.VALIDATION_FAIL;
import static com.zeroboase.reservation.type.Role.CUSTOMER;
import static com.zeroboase.reservation.type.ValidationMessage.PASSWORD_NOT_BLANK;
import static com.zeroboase.reservation.type.ValidationMessage.USERNAME_NOT_BLANK;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zeroboase.reservation.configuration.security.TokenProvider;
import com.zeroboase.reservation.dto.MemberDto;
import com.zeroboase.reservation.dto.TokenDto;
import com.zeroboase.reservation.dto.request.LoginRequestDto;
import com.zeroboase.reservation.service.MemberService;
import com.zeroboase.reservation.type.Role;
import com.zeroboase.reservation.util.MockMvcUtil;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    private final static String LOGIN_URL = "/auth/login";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private TokenProvider tokenProvider;

    private final String username = "username";
    private final String password = "123123";
    private final List<Role> roles = Collections.singletonList(CUSTOMER);

    @Test
    @DisplayName("로그인 성공")
    void successLogin() throws Exception {
        // given
        LoginRequestDto request = LoginRequestDto.builder()
            .username(username)
            .password(password)
            .build();

        given(memberService.authenticate(request))
            .willReturn(MemberDto.builder()
                .username(username)
                .roles(roles)
                .build());

        given(tokenProvider.generateToken(username, roles))
            .willReturn(TokenDto.builder()
                .accessToken("accessToken")
                .build());

        // when
        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, LOGIN_URL, request);

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").value("accessToken"));

        verify(memberService).authenticate(request);
        verify(tokenProvider).generateToken(username, roles);
    }

    @Test
    @DisplayName("로그인 실패 - 유효성 검사(username @NotBlank")
    void failLogin_username_NotBlank() throws Exception {
        // given
        // when
        LoginRequestDto request = LoginRequestDto.builder()
            .password(password)
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, LOGIN_URL, request);

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errorCode").value(VALIDATION_FAIL.toString()))
            .andExpect(jsonPath("$.description").value(USERNAME_NOT_BLANK));
    }

    @Test
    @DisplayName("로그인 실패 - 유효성 검사(password @NotBlank")
    void failLogin_password_NotBlank() throws Exception {
        // given
        // when
        LoginRequestDto request = LoginRequestDto.builder()
            .username(username)
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, LOGIN_URL, request);

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errorCode").value(VALIDATION_FAIL.toString()))
            .andExpect(jsonPath("$.description").value(PASSWORD_NOT_BLANK));
    }
}
