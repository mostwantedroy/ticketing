package com.example.ticketing.entity;

import com.example.ticketing.domain.BusinessUser;
import com.example.ticketing.type.BusinessUserType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "business_user")
@EqualsAndHashCode(of = "businessUserId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessUserId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String businessLicense;

    @Column
    @Enumerated(EnumType.STRING)
    private BusinessUserType businessUserType;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Builder
    public BusinessUserEntity(String email, String password, String businessLicense, BusinessUserType businessUserType) {
        this.email = email;
        this.password = password;
        this.businessLicense = businessLicense;
        this.businessUserType = businessUserType;
    }

    public BusinessUser toDomain() {
        return BusinessUser.builder()
                .businessUserId(businessUserId)
                .email(email)
                .password(password)
                .businessLicense(businessLicense)
                .businessUserType(businessUserType)
                .createdAt(createdAt)
                .lastModifiedAt(lastModifiedAt)
                .build();
    }

    public static BusinessUserEntity fromDomain(BusinessUser businessUser) {
        if (Objects.isNull(businessUser)) {
            return null;
        }

        return BusinessUserEntity.builder()
                .email(businessUser.getEmail())
                .password(businessUser.getPassword())
                .businessLicense(businessUser.getBusinessLicense())
                .businessUserType(businessUser.getBusinessUserType())
                .build();
    }
}
