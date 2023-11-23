package com.zeroboase.reservation.dto.request;

import static com.zeroboase.reservation.type.ValidationMessage.STORE_ADDRESS_NOT_BLANK;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_DESCRIPTION_NOT_BLANK;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_NAME_NOT_BLANK;

import com.zeroboase.reservation.domain.Store;
import jakarta.validation.constraints.NotBlank;

/**
 * 매장 등록 요청
 *
 * @param name        매장 이름
 * @param description 매장 설명
 * @param address     매장 주소
 */
public record CreateStoreRequestDto(
    @NotBlank(message = STORE_NAME_NOT_BLANK) String name,
    @NotBlank(message = STORE_DESCRIPTION_NOT_BLANK) String description,
    @NotBlank(message = STORE_ADDRESS_NOT_BLANK) String address) {
}
