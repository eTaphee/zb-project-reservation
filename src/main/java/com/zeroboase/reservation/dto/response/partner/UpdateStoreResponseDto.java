package com.zeroboase.reservation.dto.response.partner;

import lombok.Builder;

/**
 * 매장 수정 응답
 *
 * @param id          매장 아이디
 * @param name        매장 이름
 * @param description 매장 설명
 * @param tel         매장 연락처
 * @param address     매장 주소
 * @param latitude    매장 위도
 * @param longitude   매장 경도
 */
@Builder
public record UpdateStoreResponseDto(Long id, String name, String description, String tel,
                                     String address, Double latitude, Double longitude) {

}
