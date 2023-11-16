package com.example.ticketing.entity;

import com.example.ticketing.domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "user")
@EqualsAndHashCode(of = "userId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String email;

    @Column
    private String password;

    @Builder
    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User toDomain() {
        return User.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .build();
    }

    public static UserEntity fromDomain(User user) {
        if (Objects.isNull(user)) {
            return null;
        }

        return UserEntity.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
