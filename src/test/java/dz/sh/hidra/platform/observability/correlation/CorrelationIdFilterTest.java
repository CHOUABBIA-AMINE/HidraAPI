/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrelationIdFilterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.correlation
 *
 * @Description : Verifies correlation filter request and response behavior.
 *
 */
package dz.sh.hidra.platform.observability.correlation;

import dz.sh.hidra.platform.configuration.HidraPlatformProperties;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class CorrelationIdFilterTest {

    @Test
    void shouldSetResponseHeadersAndClearContext() throws Exception {
        CorrelationIdFilter filter = new CorrelationIdFilter(new CorrelationIdResolver(new HidraPlatformProperties(null, null, null, null, null)));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Correlation-Id", "correlation-1");
        request.addHeader("X-Request-Id", "request-1");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = (servletRequest, servletResponse) -> {
            assertThat(MDC.get("correlationId")).isEqualTo("correlation-1");
            assertThat(CorrelationContext.current()).isPresent();
        };

        filter.doFilter(request, response, chain);

        assertThat(response.getHeader("X-Correlation-Id")).isEqualTo("correlation-1");
        assertThat(response.getHeader("X-Request-Id")).isEqualTo("request-1");
        assertThat(CorrelationContext.current()).isEmpty();
        assertThat(MDC.get("correlationId")).isNull();
    }
}
