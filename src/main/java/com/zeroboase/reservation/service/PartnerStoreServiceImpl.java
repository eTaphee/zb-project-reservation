package com.zeroboase.reservation.service;

import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;

import com.zeroboase.reservation.domain.Store;
import com.zeroboase.reservation.dto.StoreDto;
import com.zeroboase.reservation.dto.request.CreateStoreRequestDto;
import com.zeroboase.reservation.dto.request.UpdateStoreRequestDto;
import com.zeroboase.reservation.exception.ReservationException;
import com.zeroboase.reservation.mapper.StoreMapper;
import com.zeroboase.reservation.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 파트너 상점 서비스
 */
@Service
@RequiredArgsConstructor
public class PartnerStoreServiceImpl implements PartnerStoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    /**
     * 상점 추가
     *
     * @param request 상점 추가 요청
     * @return 추가된 상점 정보
     */
    @Transactional
    @Override
    public StoreDto createStore(CreateStoreRequestDto request) {
        Store save = storeRepository.save(Store.builder()
            .name(request.name())
            .description(request.description())
            .address(request.address())
            .build());

        return StoreDto.fromEntity(save);
    }

    /**
     * 파트너 상점 정보 조회
     *
     * @param id 조회 할 상점 아이디
     * @return 상점 정보
     */
    @Transactional(readOnly = true)
    @Override
    public StoreDto readStoreById(Long id) {
        return storeRepository.findDtoById(id)
            .orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));
    }

    /**
     * 파트너 상점 목록 조회
     *
     * @return 상점 목록
     */
    @Transactional(readOnly = true)
    @Override
    public List<StoreDto> readStoreList() {
        return storeRepository.findAllDtoBy();
    }

    /**
     * 파트너 상점 수정
     *
     * @param id      수정 할 상점 아이디
     * @param request 상점 수정 요청
     */
    @Transactional
    @Override
    public StoreDto updateStore(Long id, UpdateStoreRequestDto request) {
        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));

        storeMapper.updateStoreFromDto(request, store);

        storeRepository.save(store);

        return StoreDto.fromEntity(store);
    }

    /**
     * 파트너 상점 삭제
     *
     * @param id 삭제 할 상점 아이디
     */
    @Transactional
    @Override
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}
