package com.example.ticketing.repository.performance;

import com.example.ticketing.entity.PerformanceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

public interface PerformanceCustomRepository {
    Page<PerformanceEntity> findReservablePerformancesOn(LocalDateTime localDateTime, PageRequest pageRequest);

    Page<PerformanceEntity> findBestPerformances(PageRequest pageRequest);

    Page<PerformanceEntity> findCheapestPerformances(PageRequest pageRequest);
}
