package com.zeroboase.reservation.domain.inventory.repository;

import static com.zeroboase.reservation.exception.ErrorCode.INVENTORY_NOT_FOUND;

import com.zeroboase.reservation.domain.inventory.entity.Inventory;
import com.zeroboase.reservation.exception.ReservationException;
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

    List<Inventory> findAllByStoreIdAndInventoryDateOrderByInventoryTime(Long storeId,
        LocalDate inventoryDate);

    @Query("SELECT m.username FROM Store s JOIN Inventory i ON s.id = i.storeId JOIN Member m ON s.partner.id = m.id WHERE i.id = :inventory_id")
    Optional<String> findPartnerUsernameById(@Param("inventory_id") Long id);

    default Inventory findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ReservationException(INVENTORY_NOT_FOUND));
    }
}
