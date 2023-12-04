package com.zeroboase.reservation.domain.review.service;

import com.zeroboase.reservation.domain.review.dto.CreateReview;
import com.zeroboase.reservation.domain.review.dto.UpdateReview;
import com.zeroboase.reservation.domain.review.dto.model.CustomerReviewDto;

public interface CustomerReviewService {

    Long createReview(CreateReview.Request request);

    CustomerReviewDto getReview(Long id);

    UpdateReview.Response updateReview(Long id, UpdateReview.Request request);

    void deleteReview(Long id);
}
