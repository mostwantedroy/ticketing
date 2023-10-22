package com.example.ticketing.resolver;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

public class AuthorizationResolver {
    private static final String AUTHORIZATION_HEADER_NAME = "X-Authorization";

    private AuthorizationResolver() {}

    public static Long resolveAuthorization() {
        final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (Objects.isNull(servletRequestAttributes)) {
            return null;
        }

        final String authorizationHeaderValue = servletRequestAttributes.getRequest().getHeader(AUTHORIZATION_HEADER_NAME);

        if (Objects.isNull(authorizationHeaderValue)) {
            return null;
        }

        return 0L;
    }
}
