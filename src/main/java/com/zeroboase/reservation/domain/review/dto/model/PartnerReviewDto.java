package com.zeroboase.reservation.domain.review.dto.model;

import java.time.LocalDateTime;

/**
 * 파트너 리뷰
 *
 * @param id            리뷰 아이디
 * @param content       리뷰 내용
 * @param starRating    평점
 * @param reviewedBy    리뷰 작성자
 * @param reviewedAt    리뷰 작성일시
 * @param reservationId 예약 아이디
 */
public record PartnerReviewDto(
    Long id,
    String content,
    Double starRating,
    String reviewedBy,
    LocalDateTime reviewedAt,
    Long reservationId
) {

}