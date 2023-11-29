package com.zeroboase.reservation.domain.inventory.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.zeroboase.reservation.domain.store.entity.Store;
import com.zeroboase.reservation.domain.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

/**
 * 매장 예약 재고
 * <p>
 * 파트너가 예약 가능한 재고 정보(날짜, 시간)을 등록
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"store_id", "inventory_date", "inventory_time"})})
@DynamicInsert
@Getter
@NoArgsConstructor(force = true, access = PROTECTED)
public class Inventory extends BaseEntity {

    /**
     * 재고 PK
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * 매장
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Store store;

    @Column(nullable = false, name = "store_id")
    private final Long storeId;

    /**
     * 재고 예약 가능 날짜
     */
    @Column(nullable = false, name = "inventory_date")
    private LocalDate inventoryDate;

    /**
     * 재고 예약 가능 시간
     */
    @Column(nullable = false, name = "inventory_time")
    private LocalTime inventoryTime;

    /**
     * 재고 예약 최대 수량
     */
    @Setter
    @Column(nullable = false)
    private Integer limitCount;

    /**
     * 남은 재고 수량
     */
    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer availableCount;

    @Builder
    public Inventory(Long storeId, LocalDate inventoryDate, LocalTime inventoryTime,
        Integer limitCount) {
        this.storeId = storeId;
        this.inventoryDate = inventoryDate;
        this.inventoryTime = inventoryTime;
        this.limitCount = limitCount;
    }
}
