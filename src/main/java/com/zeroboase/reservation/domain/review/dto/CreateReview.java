package com.zeroboase.reservation.domain.review.dto;

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
        Long reservationId,
        String content,
        Double starRating
    ) {

    }
}
