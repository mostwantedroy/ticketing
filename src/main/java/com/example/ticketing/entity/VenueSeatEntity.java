package com.example.ticketing.entity;

import com.example.ticketing.domain.VenueSeat;
import com.example.ticketing.type.VenueSeatType;
import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "venue_seat")
@EqualsAndHashCode(of = "seatId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VenueSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "venueId")
    private VenueEntity venue;

    @Column
    private String seatNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private VenueSeatType seatType;

    @Builder
    public VenueSeatEntity(String seatNumber, VenueSeatType seatType) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    //for jpa association
    public void apply(VenueEntity venue) {
        this.venue = venue;
    }

    public VenueSeat toDomain() {
        return VenueSeat.builder()
                .seatId(seatId)
                .seatNumber(seatNumber)
                .seatType(seatType)
                .build();
    }

    public static List<VenueSeatEntity> fromDomain(List<VenueSeat> venueSeat) {
        if (CollectionUtils.isEmpty(venueSeat)) {
            return Collections.emptyList();
        }

        return venueSeat.stream()
                .map(VenueSeatEntity::fromDomain)
                .collect(Collectors.toList());
    }

    public static VenueSeatEntity fromDomain(VenueSeat venueSeat) {
        if (Objects.isNull(venueSeat)) {
            return null;
        }

        return VenueSeatEntity.builder()
                .seatNumber(venueSeat.getSeatNumber())
                .seatType(venueSeat.getSeatType())
                .build();
    }
}