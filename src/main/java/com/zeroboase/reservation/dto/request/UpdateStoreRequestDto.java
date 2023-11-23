package com.zeroboase.reservation.dto.request;

/**
 * 매장 수정 요청
 *
 * @param name        매장 이름
 * @param description 매장 설명
 * @param address     매장 주소
 */
public record UpdateStoreRequestDto(String name, String description, String address) {

}
