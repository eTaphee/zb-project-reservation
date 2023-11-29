package com.zeroboase.reservation.domain.member.dto;

import static com.zeroboase.reservation.validator.constant.ValidationMessage.PASSWORD_NOT_BLANK;
import static com.zeroboase.reservation.validator.constant.ValidationMessage.USERNAME_NOT_BLANK;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


/**
 * 회원 가입
 */
public record RegisterMember() {

    /**
     * 회원가입 요청
     *
     * @param username 아이디
     * @param password 패스워드
     */
    @Builder
    public record Request(@NotBlank(message = USERNAME_NOT_BLANK) String username,
                          @NotBlank(message = PASSWORD_NOT_BLANK) String password) {

    }
}
