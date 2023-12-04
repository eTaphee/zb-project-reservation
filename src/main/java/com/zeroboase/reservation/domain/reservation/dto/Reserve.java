package com.zeroboase.reservation.domain.reservation.dto;

import com.zeroboase.reservation.domain.inventory.entity.Inventory;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.reservation.entity.Reservation;
import com.zeroboase.reservation.domain.reservation.entity.type.ReserveStatus;
import com.zeroboase.reservation.validator.annotation.Telephone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Reserve() {

    /**
     * 예약 요청
     *
     * @param inventoryId 재고 아이디
     * @param name        예약자 이름
     * @param tel         예약자 연락처
     * @param persons     예약 인원
     */
    public record Request(
        @NotNull Long inventoryId,
        @NotBlank String name,
        @Telephone String tel,
        @Positive Integer persons) {

        public Reservation toEntity(Inventory inventory, Member member) {
            return Reservation.builder()
                .inventory(inventory)
                .reservationPersonTel(tel)
                .reservationPeronName(name)
                .persons(persons)
                .reservedBy(member)
                .status(ReserveStatus.WAIT)
                .build();
        }
    }

    public record Response() {

    }
}
