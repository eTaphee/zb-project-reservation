package com.zeroboase.reservation.controller;

import static com.zeroboase.reservation.exception.ErrorCode.VALIDATION_FAIL;
import static com.zeroboase.reservation.domain.member.entity.type.Role.CUSTOMER;
import static com.zeroboase.reservation.validator.constant.ValidationMessage.PASSWORD_NOT_BLANK;
import static com.zeroboase.reservation.validator.constant.ValidationMessage.USERNAME_NOT_BLANK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zeroboase.reservation.configuration.security.TokenProvider;
import com.zeroboase.reservation.domain.member.controller.CustomerController;
import com.zeroboase.reservation.domain.member.dto.model.MemberDto;
import com.zeroboase.reservation.domain.member.dto.RegisterMember;
import com.zeroboase.reservation.domain.member.service.MemberService;
import com.zeroboase.reservation.util.MockMvcUtil;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {

    private final static String CUSTOMER_REGISTER_URL = "/customers/register";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("고객 회원 가입 성공")
    void successRegisterCustomer() throws Exception {
        // given
        given(memberService.registerMember(any(), any()))
            .willReturn(MemberDto.builder()
                .username("username")
                .roles(Collections.singletonList(CUSTOMER))
                .build());

        // when
        RegisterMember.Request request = RegisterMember.Request.builder()
            .username("username")
            .password("password")
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, CUSTOMER_REGISTER_URL,
            request);

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("고객 회원 가입 실패 - 유효성 검사(username @NotBlank")
    void failRegisterCustomer_username_NotBlank() throws Exception {
        // given
        // when
        RegisterMember.Request request = RegisterMember.Request.builder()
            .password("password")
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, CUSTOMER_REGISTER_URL,
            request);

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errorCode").value(VALIDATION_FAIL.toString()))
            .andExpect(jsonPath("$.description").value(USERNAME_NOT_BLANK));
    }

    @Test
    @DisplayName("고객 회원 가입 실패 - 유효성 검사(password @NotBlank")
    void failRegisterCustomer_password_NotBlank() throws Exception {
        // given
        // when
        RegisterMember.Request request = RegisterMember.Request.builder()
            .username("username")
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, CUSTOMER_REGISTER_URL,
            request);

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errorCode").value(VALIDATION_FAIL.toString()))
            .andExpect(jsonPath("$.description").value(PASSWORD_NOT_BLANK));
    }
}
