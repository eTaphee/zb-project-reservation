package com.zeroboase.reservation.repository;

import com.zeroboase.reservation.domain.Inventory;
import com.zeroboase.reservation.dto.PartnerInventoryDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    boolean existsByStoreIdAndInventoryDateAndInventoryTime(Long storeId,
        LocalDate inventoryDate, LocalTime inventoryTime);

    List<PartnerInventoryDto> findAllByStoreIdAndInventoryDateOrderByInventoryTime(Long storeId,
        LocalDate inventoryDate);

    @Query("SELECT m.username FROM Store s JOIN Inventory i ON s.id = i.storeId JOIN Member m ON s.partner.id = m.id WHERE i.id = :inventory_id")
    Optional<String> findPartnerUsernameById(@Param("inventory_id") Long id);
}
