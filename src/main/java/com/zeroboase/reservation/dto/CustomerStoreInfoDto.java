package com.zeroboase.reservation.dto;

import lombok.Builder;

/**
 * 고객용 매장 상세 정보
 *
 * @param id          매장 아이디
 * @param name        매장 이름
 * @param description 매장 설명
 * @param tel         매장 연락처
 * @param address     매장 주소
 * @param starRating  매장 평균 별점
 * @param reviewCount 매장 리뷰 수
 */
@Builder
public record CustomerStoreInfoDto(Long id, String name, String description,
                                   String address, String tel,
                                   Double starRating, Long reviewCount) {

}
