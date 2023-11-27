package com.zeroboase.reservation.dto;

import lombok.Builder;

/**
 * 고객용 매장 정보
 *
 * @param id          매장 아이디
 * @param name        매장 이름
 * @param description 매장 설명
 * @param starRating  매장 평균 별점
 * @param reviewCount 매장 리뷰 수
 * @param distance    현 위치 부터 매장까지의 거리(m)
 */
@Builder
public record CustomerStoreDto(Long id, String name, String description,
                               Double starRating, Long reviewCount, Double distance) {

}
