package com.zeroboase.reservation.service;

import com.zeroboase.reservation.domain.Member;
import com.zeroboase.reservation.dto.StoreDto;
import com.zeroboase.reservation.dto.request.CreateStoreRequestDto;
import com.zeroboase.reservation.dto.request.UpdateStoreRequestDto;
import java.util.List;

public interface PartnerStoreService {

    StoreDto createStore(CreateStoreRequestDto request);

    StoreDto readStoreById(Long id);

    // TODO: 페이징 처리 여부
    List<StoreDto> readStoreList(Member member);

    StoreDto updateStore(Long id, UpdateStoreRequestDto request);

    void deleteStore(Long id);

    boolean checkAccessReadStore(Long id, Member username);

    boolean checkAccessUpdateStore(Long id, Member username);

    boolean checkAccessDeleteStore(Long id, Member username);
}
