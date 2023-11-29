package com.zeroboase.reservation.domain.member.dto;

import static com.zeroboase.reservation.validator.constant.ValidationMessage.PASSWORD_NOT_BLANK;
import static com.zeroboase.reservation.validator.constant.ValidationMessage.USERNAME_NOT_BLANK;

import com.zeroboase.reservation.domain.member.dto.model.TokenDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * 로그인
 */
public record Login() {

    /**
     * 로그인 요청
     *
     * @param username 아이디
     * @param password 패스워드
     */
    @Builder
    public record Request(
        @NotBlank(message = USERNAME_NOT_BLANK)
        String username,
        @NotBlank(message = PASSWORD_NOT_BLANK)
        String password) {

    }

    /**
     * 로그인 응답
     *
     * @param accessToken 액세스 토큰
     */
    public record Response(String accessToken) {

        public static Response from(TokenDto token) {
            return new Response(token.accessToken());
        }
    }
}
