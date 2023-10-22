package com.example.ticketing.repository.performance;

import com.example.ticketing.entity.PerformanceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long>, PerformanceCustomRepository {
}
