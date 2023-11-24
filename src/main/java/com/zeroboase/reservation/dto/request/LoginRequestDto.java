package com.zeroboase.reservation.dto.request;

import static com.zeroboase.reservation.type.ValidationMessage.PASSWORD_NOT_BLANK;
import static com.zeroboase.reservation.type.ValidationMessage.USERNAME_NOT_BLANK;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * 로그인 요청
 *
 * @param username 아이디
 * @param password 패스워드
 */
@Builder
public record LoginRequestDto(
    @NotBlank(message = USERNAME_NOT_BLANK)
    String username,
    @NotBlank(message = PASSWORD_NOT_BLANK)
    String password) {

}
