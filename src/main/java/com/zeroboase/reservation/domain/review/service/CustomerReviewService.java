package com.zeroboase.reservation.domain.review.service;

import com.zeroboase.reservation.domain.review.dto.CreateReview;
import com.zeroboase.reservation.domain.review.dto.model.CustomerReviewDto;

public interface CustomerReviewService {

    Long createReview(CreateReview.Request request);

    CustomerReviewDto getReview(Long id);

    void deleteReview(Long id);
}
