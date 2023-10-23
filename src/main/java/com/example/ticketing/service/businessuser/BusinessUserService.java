package com.example.ticketing.service.businessuser;

import com.example.ticketing.common.ErrorCode;
import com.example.ticketing.domain.BusinessUser;
import com.example.ticketing.entity.BusinessUserEntity;
import com.example.ticketing.exception.ReservationException;
import com.example.ticketing.repository.businessuser.BusinessUserRepository;
import com.example.ticketing.service.businessuser.dto.BusinessUserLoginRequest;
import com.example.ticketing.service.businessuser.dto.BusinessUserRequest;
import com.example.ticketing.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BusinessUserService {
    private final BusinessUserRepository businessUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BusinessUser registerBusinessUser(BusinessUserRequest businessUserRequest) {
        if (Objects.isNull(businessUserRequest)) {
            throw new ReservationException("empty business user request");
        }

        final String encryptedPassword = passwordEncoder.encode(businessUserRequest.getPassword());

        final BusinessUser businessUser = BusinessUser.create()
                .email(businessUserRequest.getEmail())
                .password(encryptedPassword)
                .businessUserType(businessUserRequest.getBusinessUserType())
                .businessLicense(businessUserRequest.getBusinessLicense())
                .build();

        return businessUserRepository.save(BusinessUserEntity.fromDomain(businessUser)).toDomain();
    }

    @Transactional(readOnly = true)
    public String loginBusinessUser(BusinessUserLoginRequest businessUserLoginRequest) {
        if (Objects.isNull(businessUserLoginRequest)) {
            throw new ReservationException("empty business user login request");
        }

        final BusinessUser businessUser = businessUserRepository.findByEmail(businessUserLoginRequest.getEmail())
                .map(BusinessUserEntity::toDomain)
                .orElseThrow(() -> new ReservationException(ErrorCode.UNAUTHORIZED));

        if (!passwordEncoder.matches(businessUserLoginRequest.getPassword(), businessUser.getPassword())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        return JwtUtils.createToken(businessUser.getBusinessUserId());
    }

    @Transactional(readOnly = true)
    public BusinessUser getBusinessUser(Long businessUserId) {
        return businessUserRepository.findById(businessUserId)
                .map(BusinessUserEntity::toDomain)
                .orElseThrow(() -> new ReservationException(ErrorCode.NOT_FOUND_ENTITY));
    }




}
