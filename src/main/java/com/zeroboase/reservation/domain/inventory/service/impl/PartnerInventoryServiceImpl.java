package com.zeroboase.reservation.domain.inventory.service.impl;

import static com.zeroboase.reservation.exception.ErrorCode.CREATE_INVENTORY_FORBIDDEN;
import static com.zeroboase.reservation.exception.ErrorCode.DELETE_INVENTORY_FORBIDDEN;
import static com.zeroboase.reservation.exception.ErrorCode.INVENTORY_ALREADY_EXISTS;
import static com.zeroboase.reservation.exception.ErrorCode.INVENTORY_NOT_FOUND;
import static com.zeroboase.reservation.exception.ErrorCode.READ_INVENTORY_FORBIDDEN;
import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;
import static com.zeroboase.reservation.exception.ErrorCode.UPDATE_INVENTORY_FORBIDDEN;

import com.zeroboase.reservation.domain.inventory.dto.CreateInventory;
import com.zeroboase.reservation.domain.inventory.dto.UpdateInventory;
import com.zeroboase.reservation.domain.inventory.dto.model.PartnerInventoryDto;
import com.zeroboase.reservation.domain.inventory.entity.Inventory;
import com.zeroboase.reservation.domain.inventory.mapper.InventoryMapper;
import com.zeroboase.reservation.domain.inventory.repository.InventoryRepository;
import com.zeroboase.reservation.domain.inventory.service.PartnerInventoryService;
import com.zeroboase.reservation.domain.member.entity.Member;
import com.zeroboase.reservation.domain.store.repository.StoreRepository;
import com.zeroboase.reservation.exception.ReservationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 파트너 예약 재고 서비스
 */
@Service(value = "partnerInventoryService")
@RequiredArgsConstructor
public class PartnerInventoryServiceImpl implements PartnerInventoryService {

    private final InventoryRepository inventoryRepository;
    private final StoreRepository storeRepository;
    private final InventoryMapper inventoryMapper;

    /**
     * 재고 등록
     *
     * @param request 재고 등록 요청
     * @return 등록된 재고 정보
     */
    @Transactional
    @Override
    public CreateInventory.Response createInventory(CreateInventory.Request request) {
        validateCreateInventory(request);

        // TODO: 시간 겹치는지 확인
        // 지난 날에 대한 등록인지 확인
        //
        if (inventoryRepository.existsByStoreIdAndInventoryDateAndInventoryTime(
            request.storeId(),
            request.inventoryDate(), request.inventoryTime())) {
            throw new ReservationException(INVENTORY_ALREADY_EXISTS);
        }

        Inventory inventory = inventoryRepository.save(Inventory.builder()
            .storeId(request.storeId())
            .inventoryDate(request.inventoryDate())
            .inventoryTime(request.inventoryTime())
            .limitCount(request.limitCount())
            .availableCount(request.limitCount())
            .build());

        return inventoryMapper.mapToCreateInventoryResponse(inventory);
    }

    private void validateCreateInventory(CreateInventory.Request requestDto) {
//        if (LocalDate.now().isAfter(requestDto.inventoryDate())) {
//            throw new ReservationException(INVENTORY_DATE_IS_BEFORE_NOW);
//        }

//        if (LocalTime.now().isAfter(requestDto.inventoryTime())) {
//            throw new ReservationException(INVENTORY_TIME_IS_BEFORE_NOW);
//        }

//        if (LocalTime.now().plusHours(d))
    }

    /**
     * 파트너 재고 목록 조회
     *
     * @param storeId       매장 아이디
     * @param inventoryDate 예약 가능 날짜
     * @return 파트너 재고 목록
     */
    @Transactional(readOnly = true)
    @Override
    public List<PartnerInventoryDto> getInventoryList(Long storeId, LocalDate inventoryDate) {
        return inventoryRepository.findAllByStoreIdAndInventoryDateOrderByInventoryTime(storeId,
                inventoryDate)
            .stream()
            .map(inventoryMapper::mapToPartnerInventory)
            .collect(Collectors.toList());
    }

    /**
     * 파트너 재고 수정
     *
     * @param id      재고 아이디
     * @param request 재고 수정 요청
     * @return 수정된 재고 정보
     */
    @Transactional
    @Override
    public UpdateInventory.Response updateInventory(Long id, UpdateInventory request) {
        //  TODO: 재고 수량 체크
        // lock

        Inventory inventory = inventoryRepository.findById(id)
            .orElseThrow(() -> new ReservationException(INVENTORY_NOT_FOUND));

        inventoryMapper.updateInventoryFromDto(request, inventory);

        inventoryRepository.save(inventory);

        return inventoryMapper.mapToUpdateInventoryResponse(inventory);
    }

    /**
     * 파트너 재고 삭제
     *
     * @param id 재고 아이디
     */
    @Transactional
    @Override
    public void deleteInventory(Long id) {
        // TODO: 예약 정보 있으면 삭제 불가
        inventoryRepository.deleteById(id);
    }

    /**
     * 매장 접근 권한 확인
     *
     * @param storeId 매장 아이디
     * @param member  로그인 사용자
     * @return 등록한 파트너이면 true, 아니면 false
     */
    private boolean checkStorePermission(Long storeId, Member member) {
        String partnerUsername = storeRepository.findPartnerUsernameById(storeId)
            .orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));
        return Objects.equals(member.getUsername(), partnerUsername);
    }

    /**
     * 재고 접근 권한 확인
     *
     * @param inventoryId 재고 아이디
     * @param member      로그인 사용자
     * @return 재고 매장을 등록한 파트너이면 true, 아니면 false
     */
    private boolean checkInventoryPermission(Long inventoryId, Member member) {
        String partnerUsername = inventoryRepository.findPartnerUsernameById(inventoryId)
            .orElseThrow(() -> new ReservationException(INVENTORY_NOT_FOUND));
        return Objects.equals(member.getUsername(), partnerUsername);
    }

    /**
     * 재고 등록 권한 확인
     *
     * @param storeId 매장 아이디
     * @param member  로그인 사용자
     * @return true 또는 false
     */
    @Override
    public boolean checkAccessCreateInventory(Long storeId, Member member) {
        if (!checkStorePermission(storeId, member)) {
            throw new ReservationException(CREATE_INVENTORY_FORBIDDEN);
        }
        return true;
    }

    /**
     * 재고 읽기 권한 확인
     *
     * @param storeId 매장 아이디
     * @param member  로그인 사용자
     * @return true 또는 false
     */
    @Override
    public boolean checkAccessReadInventory(Long storeId, Member member) {
        if (!checkStorePermission(storeId, member)) {
            throw new ReservationException(READ_INVENTORY_FORBIDDEN);
        }
        return true;
    }

    /**
     * 재고 수정 권한 확인
     *
     * @param id     재고 아이디
     * @param member 로그인 사용자
     * @return true 또는 false
     */
    @Override
    public boolean checkAccessUpdateInventory(Long id, Member member) {
        if (!checkInventoryPermission(id, member)) {
            throw new ReservationException(UPDATE_INVENTORY_FORBIDDEN);
        }
        return true;
    }

    /**
     * 재고 삭제 권한 확인
     *
     * @param id     재고 아이디
     * @param member 로그인 사용자
     * @return true 또는 false
     */
    @Override
    public boolean checkAccessDeleteInventory(Long id, Member member) {
        if (!checkInventoryPermission(id, member)) {
            throw new ReservationException(DELETE_INVENTORY_FORBIDDEN);
        }
        return true;
    }
}
