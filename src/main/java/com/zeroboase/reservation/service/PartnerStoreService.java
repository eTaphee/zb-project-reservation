package com.zeroboase.reservation.service;

import com.zeroboase.reservation.domain.Member;
import com.zeroboase.reservation.dto.PartnerStoreDto;
import com.zeroboase.reservation.dto.PartnerStoreInfoDto;
import com.zeroboase.reservation.dto.request.CreateStoreRequestDto;
import com.zeroboase.reservation.dto.request.PageQueryDto;
import com.zeroboase.reservation.dto.request.UpdateStoreRequestDto;
import com.zeroboase.reservation.dto.response.PageResponseDto;

public interface PartnerStoreService {

    PartnerStoreDto createStore(CreateStoreRequestDto request);

    PartnerStoreInfoDto getStoreById(Long id);

    // TODO: 페이징 처리 여부
    PageResponseDto<PartnerStoreDto> getStoreList(PageQueryDto query);

    PartnerStoreDto updateStore(Long id, UpdateStoreRequestDto request);

    void deleteStore(Long id);

    boolean checkAccessReadStore(Long id, Member username);

    boolean checkAccessUpdateStore(Long id, Member username);

    boolean checkAccessDeleteStore(Long id, Member username);
}
