package com.example.ticketing.properties;

import com.example.ticketing.util.JwtUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    private String issuer;
    private Long validDurationSecond;

    @PostConstruct
    public void setUpJwtUtils() {
        JwtUtils.setJwtProperties(this);
    }
}
