package com.zeroboase.reservation.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 리뷰 수정
 */
public class UpdateReview {

    /**
     * 리뷰 수정 요청
     *
     * @param content    리뷰 내용
     * @param starRating 별점
     */
    public record Request(
        @NotBlank
        String content,
        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("5.0")
        Double starRating
    ) {

    }

    /**
     * 리뷰 수정 응답
     *
     * @param id         리뷰 아이디
     * @param content    리뷰 내용
     * @param starRating 별점
     * @param reviewedAt 리뷰 작성일시
     * @param updatedAt  수정일시
     */
    public record Response(Long id,
                           String content,
                           Double starRating,
                           LocalDateTime reviewedAt,
                           LocalDateTime updatedAt) {

    }
}
