package com.zeroboase.reservation.service;

import static com.zeroboase.reservation.exception.ErrorCode.DELETE_STORE_FORBIDDEN;
import static com.zeroboase.reservation.exception.ErrorCode.READ_STORE_FORBIDDEN;
import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;
import static com.zeroboase.reservation.exception.ErrorCode.UPDATE_STORE_FORBIDDEN;

import com.zeroboase.reservation.domain.Member;
import com.zeroboase.reservation.domain.Store;
import com.zeroboase.reservation.dto.StoreDto;
import com.zeroboase.reservation.dto.request.CreateStoreRequestDto;
import com.zeroboase.reservation.dto.request.UpdateStoreRequestDto;
import com.zeroboase.reservation.exception.ReservationException;
import com.zeroboase.reservation.mapper.StoreMapper;
import com.zeroboase.reservation.repository.StoreRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 파트너 상점 서비스
 */
@Service(value = "partnerStoreService")
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
    public List<StoreDto> readStoreList(Member member) {
        return storeRepository.findAllDtoByPartnerId(member.getId());
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

    /**
     * 매장을 등록한 파트너인지 확인
     *
     * @param id     매장 아이디
     * @param member 로그인 사용자
     * @return 등록한 파트너이면 true, 아니면 false
     */
    private boolean checkPermission(Long id, Member member) {
        String partnerUsername = storeRepository.findPartnerUsernameById(id)
            .orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));
        return Objects.equals(member.getUsername(), partnerUsername);
    }

    /**
     * 매장 읽기 권한 확인
     *
     * @param id     매장 아이디
     * @param member 로그인 사용자
     * @return true 또는 false
     */
    @Override
    public boolean checkAccessReadStore(Long id, Member member) {
        if (!checkPermission(id, member)) {
            throw new ReservationException(READ_STORE_FORBIDDEN);
        }
        return true;
    }

    /**
     * 매장 수정 권한 확인
     *
     * @param id     매장 아이디
     * @param member 로그인 사용자
     * @return true 또는 false
     */
    @Override
    public boolean checkAccessUpdateStore(Long id, Member member) {
        if (checkPermission(id, member)) {
            throw new ReservationException(UPDATE_STORE_FORBIDDEN);
        }
        return true;
    }

    /**
     * 매장 삭제 권한 확인
     *
     * @param id     매장 아이디
     * @param member 로그인 사용자
     * @return true 또는 false
     */
    @Override
    public boolean checkAccessDeleteStore(Long id, Member member) {
        if (checkPermission(id, member)) {
            throw new ReservationException(DELETE_STORE_FORBIDDEN);
        }
        return true;
    }
}
