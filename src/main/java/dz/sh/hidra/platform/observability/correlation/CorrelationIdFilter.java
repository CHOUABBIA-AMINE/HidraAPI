/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrelationIdFilter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.correlation
 *
 * @Description : Servlet filter that manages correlation context, MDC values, and response headers.
 *
 */
package dz.sh.hidra.platform.observability.correlation;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String MDC_CORRELATION_ID = "correlationId";
    private static final String MDC_REQUEST_ID = "requestId";

    private final CorrelationIdResolver resolver;

    public CorrelationIdFilter(CorrelationIdResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        CorrelationContext context = resolver.resolve(request);
        CorrelationContext.set(context);
        MDC.put(MDC_CORRELATION_ID, context.correlationId().value());
        MDC.put(MDC_REQUEST_ID, context.requestId().value());
        addResponseHeaders(response, context);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_CORRELATION_ID);
            MDC.remove(MDC_REQUEST_ID);
            CorrelationContext.clear();
        }
    }

    private void addResponseHeaders(HttpServletResponse response, CorrelationContext context) {
        if (resolver.correlationResponseHeaderEnabled()) {
            response.setHeader(resolver.correlationHeaderName(), context.correlationId().value());
        }
        if (resolver.requestResponseHeaderEnabled()) {
            response.setHeader(resolver.requestHeaderName(), context.requestId().value());
        }
    }
}
