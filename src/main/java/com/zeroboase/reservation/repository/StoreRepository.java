package com.zeroboase.reservation.repository;

import com.zeroboase.reservation.domain.Store;
import com.zeroboase.reservation.dto.StoreDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<StoreDto> findDtoById(Long id);

    // TODO: 왜 by를 빼면 오류가 나는지?
    List<StoreDto> findAllDtoBy();
}
