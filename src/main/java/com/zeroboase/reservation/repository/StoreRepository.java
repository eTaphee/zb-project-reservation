package com.zeroboase.reservation.repository;

import com.zeroboase.reservation.domain.Store;
import com.zeroboase.reservation.dto.StoreDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /**
     * 매장 엔티티를 DTO로 변환
     *
     * @param id 매장 아이디
     * @return 매장 DTO
     */
    Optional<StoreDto> findDtoById(Long id);

    // TODO: 왜 by를 빼면 오류가 나는지?

    /**
     * 모든 매장 엔티티를 조회
     *
     * @return 매장 DTO 리스트
     */
    List<StoreDto> findAllDtoByPartnerId(Long partnerId);

    /**
     * 매장의 파트너 아이디 조회
     *
     * @param id 매장 아이디
     * @return 파트너 아이디
     */
    @Query("SELECT m.username FROM Store s JOIN Member m ON s.partner.id = m.id WHERE s.id = :store_id")
    Optional<String> findPartnerUsernameById(@Param("store_id") Long id);
}
