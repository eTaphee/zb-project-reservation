package com.zeroboase.reservation.domain.store.repository;

import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;

import com.zeroboase.reservation.domain.store.dto.model.CustomerStoreDto;
import com.zeroboase.reservation.domain.store.dto.model.CustomerStoreInfoDto;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreDto;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreInfoDto;
import com.zeroboase.reservation.domain.store.entity.Store;
import com.zeroboase.reservation.exception.ReservationException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /**
     * 파트너 매장 상세 정보 조회
     *
     * @param id 매장 아이디
     * @return 파트너 매장 상세 정보
     */
    Optional<PartnerStoreInfoDto> findPartnerStoreInfoById(Long id);

    // TODO: 왜 by를 빼면 오류가 나는지?

    /**
     * 파트너 매장 목록 페이징 조회
     *
     * @param pageable  페이징 정보
     * @param partnerId 파트너 아이디
     * @return 페이징된 파트너 매장 목록
     */
    Page<PartnerStoreDto> findAllDtoByPartnerId(Pageable pageable, Long partnerId);

    /**
     * 매장의 파트너 아이디 조회
     *
     * @param id 매장 아이디
     * @return 파트너 아이디
     */
    @Query("SELECT m.username FROM Store s JOIN Member m ON s.partner.id = m.id WHERE s.id = :store_id")
    Optional<String> findPartnerUsernameById(@Param("store_id") Long id);

    @Query("SELECT new com.zeroboase.reservation.domain.store.dto.model.CustomerStoreDto(id , name, description, starRating, reviewCount, CAST(ST_DISTANCE_SPHERE(point(:sourceLongitude, :sourceLatitude), point(longitude, latitude)) AS DOUBLE) AS distance) FROM Store")
    Page<CustomerStoreDto> findAllCustomerStoreDto(Pageable pageable,
        @Param("sourceLatitude") Double latitude,
        @Param("sourceLongitude") Double longitude);

    Optional<CustomerStoreInfoDto> findCustomStoreInfoById(Long id);

    default Store findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ReservationException(STORE_NOT_FOUND));
    }
}
