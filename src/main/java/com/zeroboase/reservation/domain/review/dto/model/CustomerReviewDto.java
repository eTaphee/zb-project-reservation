package com.zeroboase.reservation.domain.review.dto.model;

import java.time.LocalDateTime;

/**
 * 고객 리뷰
 *
 * @param id   리뷰 아이디
 * @param content    리뷰 내용
 * @param starRating 평점
 * @param reviewedAt 리뷰 작성일시
 */
public record CustomerReviewDto(
    Long id,
    String content,
    Double starRating,
    LocalDateTime reviewedAt
) {

}
