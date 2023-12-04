package com.zeroboase.reservation.domain.review.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.zeroboase.reservation.domain.common.entity.BaseEntity;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedBy;

@Entity
@DynamicInsert
@Getter
@NoArgsConstructor(force = true, access = PROTECTED)
public class Review extends BaseEntity {

    /**
     * 리뷰 PK
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * 리뷰 내용
     */
    private String content;

    /**
     * 예약 정보
     */
    @OneToOne(fetch = LAZY, optional = false)
    private Reservation reservation;

    /**
     * 별점
     */
    @ColumnDefault("0")
    @Column(nullable = false)
    private Double starRating;

    /**
     * 리뷰 작성자
     */
    @ManyToOne(fetch = LAZY, optional = false)
    @CreatedBy
    private Member createdBy;

    @Builder
    public Review(Reservation reservation, String content, Double starRating) {
        this.reservation = reservation;
        this.content = content;
        this.starRating = starRating;
    }
}
