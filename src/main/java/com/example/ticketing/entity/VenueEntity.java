package com.example.ticketing.entity;


import com.example.ticketing.domain.Venue;
import com.example.ticketing.type.VenueType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "venue")
@EqualsAndHashCode(of = "venueId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VenueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long venueId;

    @Column
    private Long businessUserId;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private VenueType venueType;

    @Column
    private LocalTime runningStartedAt;

    @Column
    private LocalTime runningEndedAt;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VenueSeatEntity> venueSeats = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    private Long createdBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Column
    private Long lastModifiedBy;

    @Builder
    public VenueEntity(Long businessUserId, String name, VenueType venueType, LocalTime runningStartedAt,
                       LocalTime runningEndedAt, List<VenueSeatEntity> venueSeats) {

        this.businessUserId = businessUserId;
        this.name = name;
        this.venueType = venueType;
        this.runningStartedAt = runningStartedAt;
        this.runningEndedAt = runningEndedAt;
        this.venueSeats = venueSeats;
        this.venueSeats.forEach(venueSeat -> venueSeat.apply(this));
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

    public Venue toDomain() {
        return Venue.builder()
                .venueId(venueId)
                .businessUserId(businessUserId)
                .name(name)
                .venueType(venueType)
                .runningStartedAt(runningStartedAt)
                .runningEndedAt(runningEndedAt)
                .venueSeats(venueSeats.stream()
                        .map(VenueSeatEntity::toDomain)
                        .collect(Collectors.toList()))
                .createdAt(createdAt)
                .createdBy(createdBy)
                .lastModifiedAt(lastModifiedAt)
                .lastModifiedBy(lastModifiedBy)
                .build();
    }

    public static VenueEntity fromDomain(Venue venue) {
        if (Objects.isNull(venue)) {
            return null;
        }

        return VenueEntity.builder()
                .businessUserId(venue.getBusinessUserId())
                .name(venue.getName())
                .venueType(venue.getVenueType())
                .runningStartedAt(venue.getRunningStartedAt())
                .runningEndedAt(venue.getRunningEndedAt())
                .venueSeats(VenueSeatEntity.fromDomain(venue.getVenueSeats()))
                .build();
    }
}
