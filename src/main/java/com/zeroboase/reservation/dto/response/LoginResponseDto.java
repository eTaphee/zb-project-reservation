package com.zeroboase.reservation.dto.response;

import com.zeroboase.reservation.dto.TokenDto;

/**
 * 로그인 응답
 *
 * @param accessToken 액세스 토큰
 */
public record LoginResponseDto(String accessToken) {

    public static LoginResponseDto from(TokenDto token) {
        return new LoginResponseDto(token.accessToken());
    }
}
