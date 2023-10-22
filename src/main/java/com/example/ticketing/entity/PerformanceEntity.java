package com.example.ticketing.entity;

import com.example.ticketing.domain.Performance;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


@Getter
@Entity
@Table(name = "performance")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "performanceId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long performanceId;

    @JoinColumn(name = "venueId")
    @ManyToOne(fetch = FetchType.LAZY)
    private VenueEntity venue;

    @Column
    private Long businessUserId;

    @Column
    private String name;

    @Column
    private Integer totalCapacity;

    @Column
    private Integer remainCapacity;

    @Column
    private LocalDateTime startAt;

    @Column
    private LocalDateTime endAt;

    @Column
    private BigDecimal normalPrice;

    @Column
    private BigDecimal vipPrice;

    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    private Long createdBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Column
    private Long lastModifiedBy;

    @Builder
    public PerformanceEntity(VenueEntity venue, Long businessUserId, String name, Integer totalCapacity, Integer remainCapacity,
                             LocalDateTime startAt, LocalDateTime endAt, BigDecimal normalPrice, BigDecimal vipPrice) {

        this.venue = venue;
        this.businessUserId = businessUserId;
        this.name = name;
        this.totalCapacity = totalCapacity;
        this.remainCapacity = remainCapacity;
        this.startAt = startAt;
        this.endAt = endAt;
        this.normalPrice = normalPrice;
        this.vipPrice = vipPrice;
    }

    @PrePersist
    public void beforePersist() {
        this.createdBy = businessUserId;
        this.lastModifiedBy = businessUserId;
    }

    @PreUpdate
    public void beforeUpdate() {
        this.lastModifiedBy = businessUserId;
    }

    public Performance toDomain() {
        return Performance.builder()
                .performanceId(performanceId)
                .venue(venue.toDomain())
                .businessUserId(businessUserId)
                .name(name)
                .totalCapacity(totalCapacity)
                .remainCapacity(remainCapacity)
                .startAt(startAt)
                .endAt(endAt)
                .normalPrice(normalPrice)
                .vipPrice(vipPrice)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .lastModifiedAt(lastModifiedAt)
                .lastModifiedBy(lastModifiedBy)
                .build();
    }

    public static PerformanceEntity fromDomain(VenueEntity venue, Performance performance) {
        if (Objects.isNull(performance)) {
            return null;
        }

        return PerformanceEntity.builder()
                .venue(venue)
                .businessUserId(performance.getBusinessUserId())
                .name(performance.getName())
                .totalCapacity(performance.getTotalCapacity())
                .remainCapacity(performance.getRemainCapacity())
                .startAt(performance.getStartAt())
                .endAt(performance.getEndAt())
                .normalPrice(performance.getNormalPrice())
                .vipPrice(performance.getVipPrice())
                .build();
    }

    public void write(Integer remainCapacity) {
        this.remainCapacity = remainCapacity;
    }
}
