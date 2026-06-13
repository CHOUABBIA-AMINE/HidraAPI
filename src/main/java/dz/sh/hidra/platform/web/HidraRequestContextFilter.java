/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRequestContextFilter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.web
 *
 * @Description : Populates request, correlation, actor, organization, and tenant context for every HTTP request.
 *
 */
package dz.sh.hidra.platform.web;


import dz.sh.hidra.platform.observability.LoggingContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Populates platform request context and MDC values for every HTTP request.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public final class HidraRequestContextFilter extends OncePerRequestFilter {

    private static final String UNKNOWN_ACTOR = "anonymous";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String correlationId = resolveHeader(request, PlatformHeaders.CORRELATION_ID, UUID.randomUUID().toString());
        String requestId = resolveHeader(request, PlatformHeaders.REQUEST_ID, UUID.randomUUID().toString());
        String actorId = resolveHeader(request, PlatformHeaders.ACTOR_ID, UNKNOWN_ACTOR);
        String organizationScope = normalize(request.getHeader(PlatformHeaders.ORGANIZATION_SCOPE));
        String tenantId = normalize(request.getHeader(PlatformHeaders.TENANT_ID));

        try {
            LoggingContext.putCorrelationId(correlationId);
            LoggingContext.putRequestId(requestId);
            LoggingContext.putActorId(actorId);
            MDC.put(LoggingContext.CORRELATION_ID, correlationId);
            MDC.put(LoggingContext.REQUEST_ID, requestId);
            MDC.put(LoggingContext.ACTOR_ID, actorId);
            putIfPresent("organizationScope", organizationScope);
            putIfPresent("tenantId", tenantId);

            response.setHeader(PlatformHeaders.CORRELATION_ID, correlationId);
            response.setHeader(PlatformHeaders.REQUEST_ID, requestId);
            filterChain.doFilter(request, response);
        } finally {
            LoggingContext.clearPlatformContext();
            MDC.remove(LoggingContext.CORRELATION_ID);
            MDC.remove(LoggingContext.REQUEST_ID);
            MDC.remove(LoggingContext.ACTOR_ID);
            MDC.remove("organizationScope");
            MDC.remove("tenantId");
        }
    }

    private static String resolveHeader(HttpServletRequest request, String headerName, String defaultValue) {
        String value = normalize(request.getHeader(headerName));
        return value == null ? defaultValue : value;
    }

    private static void putIfPresent(String key, String value) {
        if (value != null) {
            MDC.put(key, value);
            LoggingContext.put(key, value);
        }
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
