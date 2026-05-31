/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrelationIdResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.correlation
 *
 * @Description : Resolves incoming correlation identifiers or generates missing request identifiers.
 *
 */
package dz.sh.hidra.platform.observability.correlation;

import dz.sh.hidra.kernel.domain.value.CorrelationId;
import dz.sh.hidra.kernel.domain.value.RequestId;
import dz.sh.hidra.platform.configuration.HidraPlatformProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CorrelationIdResolver {

    private final HidraPlatformProperties properties;

    public CorrelationIdResolver(HidraPlatformProperties properties) {
        this.properties = properties;
    }

    public CorrelationContext resolve(HttpServletRequest request) {
        return new CorrelationContext(
                resolveCorrelationId(headerValue(request, correlationHeaderName())),
                resolveRequestId(headerValue(request, requestHeaderName())));
    }

    public CorrelationId resolveCorrelationId(String candidate) {
        if (candidate == null || candidate.isBlank()) {
            return CorrelationId.newId();
        }
        return CorrelationId.of(candidate);
    }

    public RequestId resolveRequestId(String candidate) {
        if (candidate == null || candidate.isBlank()) {
            return RequestId.newId();
        }
        return RequestId.of(candidate);
    }

    public String correlationHeaderName() {
        return properties.observability().correlation().headerName();
    }

    public String requestHeaderName() {
        return properties.observability().request().headerName();
    }

    public boolean correlationResponseHeaderEnabled() {
        return properties.observability().correlation().responseHeaderEnabled();
    }

    public boolean requestResponseHeaderEnabled() {
        return properties.observability().request().responseHeaderEnabled();
    }

    private static String headerValue(HttpServletRequest request, String headerName) {
        return request.getHeader(headerName);
    }
}
