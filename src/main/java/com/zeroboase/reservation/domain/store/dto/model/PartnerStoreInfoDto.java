package com.zeroboase.reservation.domain.store.dto.model;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * 파트너 매장 상세 정보
 *
 * @param id           매장 아이디
 * @param name         매장 이름
 * @param description  매장 설명
 * @param tel          매장 연락처
 * @param address      매장 주소
 * @param starRating   매장 평균 별점
 * @param reviewCount  매장 리뷰 수
 * @param registeredAt 매장 등록일시
 */
@Builder
public record PartnerStoreInfoDto(Long id, String name, String description, String tel,
                                  String address, Double starRating, Long reviewCount,
                                  LocalDateTime registeredAt) {

}
