package com.example.ticketing.service.user;

import com.example.ticketing.common.ErrorCode;
import com.example.ticketing.domain.User;
import com.example.ticketing.entity.UserEntity;
import com.example.ticketing.exception.ReservationException;
import com.example.ticketing.repository.user.UserRepository;
import com.example.ticketing.service.user.dto.UserLoginRequest;
import com.example.ticketing.service.user.dto.UserRequest;
import com.example.ticketing.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRequest userRequest) {
        if (Objects.isNull(userRequest)) {
            throw new ReservationException("empty user request");
        }

        final String encryptedPassword = passwordEncoder.encode(userRequest.getPassword());

        final User user = User.create()
                .email(userRequest.getEmail())
                .password(encryptedPassword)
                .build();

        return userRepository.save(UserEntity.fromDomain(user)).toDomain();
    }

    public String loginUser(UserLoginRequest userLoginRequest) {
        if (Objects.isNull(userLoginRequest)) {
            throw new ReservationException("empty user login request");
        }

        final User user = userRepository.findByEmail(userLoginRequest.getEmail())
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new ReservationException(ErrorCode.UNAUTHORIZED));

        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        return JwtUtils.createToken(user.getUserId());
    }


    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new ReservationException(ErrorCode.NOT_FOUND_ENTITY));
    }





}
