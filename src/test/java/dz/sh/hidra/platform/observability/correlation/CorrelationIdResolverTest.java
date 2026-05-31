/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrelationIdResolverTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.correlation
 *
 * @Description : Verifies incoming and generated correlation ID resolution.
 *
 */
package dz.sh.hidra.platform.observability.correlation;

import dz.sh.hidra.platform.configuration.HidraPlatformProperties;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class CorrelationIdResolverTest {

    private final CorrelationIdResolver resolver = new CorrelationIdResolver(new HidraPlatformProperties(null, null, null, null, null));

    @Test
    void shouldPreserveIncomingHeaders() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Correlation-Id", " correlation-1 ");
        request.addHeader("X-Request-Id", " request-1 ");

        CorrelationContext context = resolver.resolve(request);

        assertThat(context.correlationId().value()).isEqualTo("correlation-1");
        assertThat(context.requestId().value()).isEqualTo("request-1");
    }

    @Test
    void shouldGenerateMissingHeaders() {
        CorrelationContext context = resolver.resolve(new MockHttpServletRequest());

        assertThat(context.correlationId().value()).isNotBlank();
        assertThat(context.requestId().value()).isNotBlank();
    }
}
