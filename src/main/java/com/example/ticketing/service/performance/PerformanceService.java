package com.example.ticketing.service.performance;

import com.example.ticketing.common.ErrorCode;
import com.example.ticketing.domain.BusinessUser;
import com.example.ticketing.domain.Performance;
import com.example.ticketing.domain.Venue;
import com.example.ticketing.entity.PerformanceEntity;
import com.example.ticketing.entity.VenueEntity;
import com.example.ticketing.entity.BusinessUserEntity;
import com.example.ticketing.exception.ReservationException;
import com.example.ticketing.repository.businessuser.BusinessUserRepository;
import com.example.ticketing.repository.performance.PerformanceRepository;
import com.example.ticketing.repository.venue.VenueRepository;
import com.example.ticketing.service.performance.dto.PerformanceRequest;
import com.example.ticketing.type.BusinessUserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceService {
    private final PerformanceRepository performanceRepository;
    private final VenueRepository venueRepository;
    private final BusinessUserRepository businessUserRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCapacity(Long performanceId, Integer requestedCapacity) {
        final PerformanceEntity performanceEntity = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new ReservationException(ErrorCode.NOT_FOUND_ENTITY));

        final Performance performance = performanceEntity.toDomain();
        performance.decreaseCapacity(requestedCapacity);

        performanceEntity.write(performance.getRemainCapacity());
    }

    public Performance registerPerformance(Long venueId, Long businessUserId, PerformanceRequest performanceRequest) {
        if (Objects.isNull(performanceRequest)) {
            throw new ReservationException("empty performance request");
        }

        final BusinessUser businessUser = businessUserRepository.findById(businessUserId)
                .map(BusinessUserEntity::toDomain)
                .orElseThrow(() -> new ReservationException(ErrorCode.NOT_FOUND_ENTITY));

        if (BusinessUserType.PERFORMANCE_ADMIN != businessUser.getBusinessUserType()) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        final VenueEntity venueEntity = venueRepository.findByVenueId(venueId)
                .orElseThrow(() -> new ReservationException(ErrorCode.NOT_FOUND_ENTITY));

        final Venue venue = venueEntity.toDomain();

        final Performance performance = createPerformance(venue, businessUserId, performanceRequest);

        return performanceRepository.save(PerformanceEntity.fromDomain(venueEntity, performance)).toDomain();
    }

    private Performance createPerformance(Venue venue, Long businessUserId, PerformanceRequest performanceRequest) {
        return Performance.create()
                .venue(venue)
                .businessUserId(businessUserId)
                .name(performanceRequest.getName())
                .totalCapacity(performanceRequest.getCapacity())
                .startAt(performanceRequest.getStartAt())
                .endAt(performanceRequest.getEndAt())
                .normalPrice(performanceRequest.getNormalPrice())
                .vipPrice(performanceRequest.getVipPrice())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<Performance> findReservablePerformancesOn(PageRequest pageRequest) {
        final Page<PerformanceEntity> pagedPerformances = performanceRepository.findReservablePerformancesOn(LocalDateTime.now(), pageRequest);

        return getPagedPerformance(pagedPerformances, pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<Performance> findBestPerformances(PageRequest pageRequest) {
        final Page<PerformanceEntity> pagedPerformances = performanceRepository.findBestPerformances(pageRequest);

        return getPagedPerformance(pagedPerformances, pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<Performance> findCheapestPerformances(PageRequest pageRequest){
        final Page<PerformanceEntity> pagedPerformances = performanceRepository.findCheapestPerformances(pageRequest);

        return getPagedPerformance(pagedPerformances, pageRequest);
    }

    private Page<Performance> getPagedPerformance(Page<PerformanceEntity> pagedPerformanceEntities, PageRequest pageRequest) {
        if (Objects.isNull(pagedPerformanceEntities) || CollectionUtils.isEmpty(pagedPerformanceEntities.getContent())) {
            return Page.empty();
        }

        return new PageImpl<>(
                pagedPerformanceEntities.getContent()
                        .stream()
                        .map(PerformanceEntity::toDomain)
                        .collect(Collectors.toList()),
                pageRequest,
                pagedPerformanceEntities.getTotalElements()
        );
    }

}
