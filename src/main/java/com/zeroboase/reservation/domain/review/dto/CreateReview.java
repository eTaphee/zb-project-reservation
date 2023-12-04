package com.zeroboase.reservation.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 리뷰 작성
 */
public record CreateReview() {

    /**
     * 리뷰 작성 요청
     *
     * @param reservationId 예약 아이디
     * @param content       리뷰 내용
     * @param starRating    별점
     */
    public record Request(
        @NotNull
        Long reservationId,
        @NotBlank
        String content,
        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("5.0")
        Double starRating
    ) {

    }
}
