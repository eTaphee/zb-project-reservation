package com.zeroboase.reservation.domain.review.controller;

import com.zeroboase.reservation.domain.review.dto.CreateReview;
import com.zeroboase.reservation.domain.review.dto.UpdateReview;
import com.zeroboase.reservation.domain.review.dto.model.CustomerReviewDto;
import com.zeroboase.reservation.domain.review.service.CustomerReviewService;
import com.zeroboase.reservation.util.ResponseEntityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 고객 리뷰 controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/reviews")
public class CustomReviewController {

    private final CustomerReviewService customerReviewService;

    /**
     * 리뷰 작성
     *
     * @param request 리뷰 작성 요청
     * @return 리뷰 아이디
     */
    @PreAuthorize("hasRole('CUSTOMER') and @reservationSecurity.checkAccessAsCustomer(#request.reservationId)")
    @PostMapping()
    public ResponseEntity<Void> createReview(
        @Valid @RequestBody CreateReview.Request request) {
        return ResponseEntityUtil.created(customerReviewService.createReview(request));
    }

    /**
     * 리뷰 조회
     *
     * @param id 리뷰 아이디
     * @return 리뷰
     */
    @PreAuthorize("hasRole('CUSTOMER') and @reviewSecurity.checkAccessAsCustomer(#id)")
    @GetMapping("{id}")
    public ResponseEntity<CustomerReviewDto> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(customerReviewService.getReview(id));
    }

    /**
     * 리뷰 수정
     *
     * @param id      리뷰 아이디
     * @param request 리뷰 수정 요청
     * @return 리뷰
     */
    @PreAuthorize("hasRole('CUSTOMER') and @reviewSecurity.checkAccessAsCustomer(#id)")
    @PatchMapping("{id}")
    public ResponseEntity<UpdateReview.Response> updateReview(
        @PathVariable Long id,
        @Valid @RequestBody UpdateReview.Request request) {
        return ResponseEntity.ok(customerReviewService.updateReview(id, request));
    }

    /**
     * 리뷰 삭제
     *
     * @param id 리뷰 아이디
     * @return 201
     */
    @PreAuthorize("hasRole('CUSTOMER') and @reviewSecurity.checkAccessAsCustomer(#id)")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        customerReviewService.deleteReview(id);
        return ResponseEntityUtil.noContent();
    }
}
