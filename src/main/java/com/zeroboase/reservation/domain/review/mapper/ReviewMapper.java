package com.zeroboase.reservation.domain.review.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.zeroboase.reservation.domain.review.dto.UpdateReview;
import com.zeroboase.reservation.domain.review.dto.model.CustomerReviewDto;
import com.zeroboase.reservation.domain.review.dto.model.PartnerReviewDto;
import com.zeroboase.reservation.domain.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface ReviewMapper {

    @Mapping(source = "review.createdAt", target = "reviewedAt")
    CustomerReviewDto mapToCustomerReview(Review review);

    @Mapping(source = "review.createdBy.username", target = "reviewedBy")
    @Mapping(source = "review.createdAt", target = "reviewedAt")
    @Mapping(source = "review.reservation.id", target = "reservationId")
    PartnerReviewDto mapToPartnerReview(Review review);

    UpdateReview.Response mapToUpdateReviewResponse(Review review);
}
