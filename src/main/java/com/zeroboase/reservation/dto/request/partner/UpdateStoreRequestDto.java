package com.zeroboase.reservation.dto.request.partner;

import static com.zeroboase.reservation.type.RegexPattern.TEL;
import static com.zeroboase.reservation.type.ValidationMessage.INVALID_TEL_FORMAT;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_LATITUDE_RANGE;
import static com.zeroboase.reservation.type.ValidationMessage.STORE_LONGITUDE_RANGE;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

/**
 * 매장 수정 요청
 * <p>
 * 위도, 경도는 WGS84 좌표계 사용
 *
 * @param name        매장 이름
 * @param description 매장 설명
 * @param tel         매장 연락처
 * @param address     매장 주소
 * @param latitude    매장 위도
 * @param longitude   매장 경도
 */
@Builder
public record UpdateStoreRequestDto(
    String name,
    String description,
    @Pattern(regexp = TEL, message = INVALID_TEL_FORMAT)
    String tel,
    String address,
    @Range(min = 32, max = 39, message = STORE_LATITUDE_RANGE)
    Double latitude,
    @Range(min = 124, max = 131, message = STORE_LONGITUDE_RANGE)
    Double longitude) {

}

// TODO: name, description, address null이 아닌 경우 NotBlank 유효성 검사가 필요