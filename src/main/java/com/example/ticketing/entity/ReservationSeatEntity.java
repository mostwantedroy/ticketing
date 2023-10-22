package com.example.ticketing.entity;


import com.example.ticketing.domain.ReservationSeat;
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
@Table(name = "reservation_seat")
@EqualsAndHashCode(of = "reservationSeatId")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationSeatId;

    @JoinColumn(name = "reservationId")
    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationEntity reservation;

    @Column
    private Long venueSeatId;

    @Column
    @Enumerated(EnumType.STRING)
    private VenueSeatType seatType;

    @Builder
    public ReservationSeatEntity(Long venueSeatId, VenueSeatType seatType) {
        this.venueSeatId = venueSeatId;
        this.seatType = seatType;
    }

    //for jpa association
    public void apply(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public ReservationSeat toDomain() {
        return ReservationSeat.builder()
                .reservationSeatId(reservationSeatId)
                .venueSeatId(venueSeatId)
                .seatType(seatType)
                .build();
    }

    public static List<ReservationSeatEntity> fromDomain(List<ReservationSeat> reservationSeats) {
        if (CollectionUtils.isEmpty(reservationSeats)) {
            return Collections.emptyList();
        }

        return reservationSeats.stream()
                .map(ReservationSeatEntity::fromDomain)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static ReservationSeatEntity fromDomain(ReservationSeat reservationSeat) {
        if (Objects.isNull(reservationSeat)) {
            return null;
        }

        return ReservationSeatEntity.builder()
                .venueSeatId(reservationSeat.getVenueSeatId())
                .seatType(reservationSeat.getSeatType())
                .build();
    }
}
