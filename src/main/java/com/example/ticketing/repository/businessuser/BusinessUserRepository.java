package com.example.ticketing.repository.businessuser;

import com.example.ticketing.entity.BusinessUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessUserRepository extends JpaRepository<BusinessUserEntity, Long> {
    Optional<BusinessUserEntity> findByEmail(String email);
}
