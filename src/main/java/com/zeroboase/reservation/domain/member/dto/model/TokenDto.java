package com.zeroboase.reservation.domain.member.dto.model;

import lombok.Builder;

/**
 * 토큰 정보
 *
 * @param accessToken 액세스 토큰
 */
@Builder
public record TokenDto(String accessToken) {

}
