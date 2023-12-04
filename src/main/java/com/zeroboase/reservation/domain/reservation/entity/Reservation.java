package com.zeroboase.reservation.domain.reservation.entity;

import static com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus.APPROVE;
import static com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus.CANCEL;
import static com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus.REJECT;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.zeroboase.reservation.domain.common.entity.BaseEntity;
import com.zeroboase.reservation.domain.inventory.entity.Inventory;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReserveStatus status;

    /**
     * 예약 매장 정보
     */
    @ManyToOne(fetch = LAZY, optional = false)
    private Inventory inventory;

    /**
     * 예약자 연락처
     */
    @Column(nullable = false)
    private final String reservationPersonTel;

    /**
     * 예약자 이름
     * <p>
     * 예약한 사람과 이용하는 사람이 다른 경우
     */
    @Column(nullable = false)
    private final String reservationPeronName;

    /**
     * 이용 인원
     */
    @Column(nullable = false)
    private final Integer persons;

    /**
     * 예약한 사람
     */
    @ManyToOne(fetch = LAZY, optional = false)
    private Member reservedBy;

    /**
     * 예약 일시
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime reservedAt;

    /**
     * 체크인 여부
     */
    @Column(nullable = false)
    private boolean checkedIn;

    /**
     * 체크인 시간
     */
    private LocalDateTime checkedInAt;

    @Builder
    public Reservation(Inventory inventory, String reservationPersonTel,
        String reservationPeronName, Integer persons, Member reservedBy, ReserveStatus status) {
        this.inventory = inventory;
        this.reservationPersonTel = reservationPersonTel;
        this.reservationPeronName = reservationPeronName;
        this.persons = persons;
        this.reservedBy = reservedBy;
        this.status = status;
    }

    /**
     * 예약 취소
     */
    public void cancel() {
        this.status = CANCEL;
        this.inventory.setAvailableCount(this.inventory.getAvailableCount() + 1);
    }

    /**
     * 예약 승인
     */
    public void approve() {
        this.status = APPROVE;
    }

    /**
     * 예약 거절
     */
    public void reject() {
        this.status = REJECT;
    }

    /**
     * 체크인
     */
    public void checkIn() {
        this.checkedIn = true;
        this.checkedInAt = LocalDateTime.now();
    }
}
