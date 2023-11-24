package com.zeroboase.reservation.dto;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * 파트너 매장 기본 정보
 *
 * @param id           매장 아이디
 * @param name         매장 이름
 * @param starRating   매장 평균 별점
 * @param reviewCount  매장 리뷰 수
 * @param registeredAt 매장 등록일시
 */
@Builder
public record PartnerStoreDto(Long id, String name, Double starRating, Long reviewCount,
                              LocalDateTime registeredAt) {

}
