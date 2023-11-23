package com.zeroboase.reservation.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(force = true, access = PROTECTED)
public class Store extends BaseEntity {

    /**
     * 매장 PK
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * 매장 이름
     */
    @Setter
    private String name;

    /**
     * 매장 설명
     */
    @Setter
    private String description;

    /**
     * 매장 주소
     */
    @Setter
    private String address;

    /**
     * 매장 위치 위도
     */
    @Setter
    private Double longitude;

    /**
     * 매장 위치 경도
     */
    @Setter
    private Double latitude;

    /**
     * 매장 등록일시
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registeredAt;

    /**
     * 매장 소유 파트너
     */
    @ManyToOne(fetch = LAZY)
    @CreatedBy
    private Member partner;

    @Builder
    public Store(String name, String description, String address, Double longitude,
        Double latitude) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
