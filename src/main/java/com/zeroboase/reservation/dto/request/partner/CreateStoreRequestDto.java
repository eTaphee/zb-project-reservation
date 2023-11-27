package com.zeroboase.reservation.dto.request.partner;

import static com.zeroboase.reservation.type.RegexPattern.TEL;
import static com.zeroboase.reservation.type.ValidationMessage.INVALID_TEL_FORMAT;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_ADDRESS_NOT_BLANK;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_DESCRIPTION_NOT_BLANK;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_LATITUDE_NOT_NULL;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_LATITUDE_RANGE;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_LONGITUDE_NOT_NULL;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_LONGITUDE_RANGE;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_NAME_NOT_BLANK;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_TEL_NOT_BLANK;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

/**
 * 매장 등록 요청
 * <p>
 * 위도, 경도는 WGS84 좌표계 사용
 *
 * @param name        매장 이름
 * @param description 매장 설명
 * @param address     매장 주소
 * @param latitude    매장 위도
 * @param longitude   매장 경도
 */
@Builder
public record CreateStoreRequestDto(
    @NotBlank(message = STORE_NAME_NOT_BLANK)
    String name,
    @NotBlank(message = STORE_DESCRIPTION_NOT_BLANK)
    String description,
    @NotBlank(message = STORE_TEL_NOT_BLANK)
    @Pattern(regexp = TEL, message = INVALID_TEL_FORMAT)
    String tel,
    @NotBlank(message = STORE_ADDRESS_NOT_BLANK) String address,
    @NotNull(message = STORE_LATITUDE_NOT_NULL)
    @Range(min = 32, max = 39, message = STORE_LATITUDE_RANGE)
    Double latitude,
    @NotNull(message = STORE_LONGITUDE_NOT_NULL)
    @Range(min = 124, max = 131, message = STORE_LONGITUDE_RANGE)
    Double longitude) {

}
