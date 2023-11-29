package com.zeroboase.reservation.domain.reservation.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.zeroboase.reservation.domain.common.entity.BaseEntity;
import com.zeroboase.reservation.domain.inventory.entity.Inventory;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(force = true, access = PROTECTED)
public class Reservation extends BaseEntity {

    /**
     * 예약 PK
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * 예약 상태
     */
    @Column(nullable = false)
    private ReserveStatus status;

    /**
     * 예약 매장 정보
     */
    @OneToOne(fetch = LAZY)
    private Inventory inventory;

    /**
     * 예약자 연락처
     */
    @Column(nullable = false)
    private String reservationPersonTel;

    /**
     * 예약자 이름
     * <p>
     * 예약한 사람과 이용하는 사람이 다른 경우
     */
    @Column(nullable = false)
    private String reservationPeronName;

    /**
     * 이용 인원
     */
    @Column(nullable = false)
    private Integer reservedPersonCount;

    /**
     * 예약한 사람
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reserved_by_id", insertable = false, updatable = false)
    private Member reservedBy;

    @Column(nullable = false, name = "reserved_by_id")
    private final Long reservedById;

    /**
     * 예약 일시
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime reservedAt;
}
