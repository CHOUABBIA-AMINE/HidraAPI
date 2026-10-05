/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRequestContextFilterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.web
 *
 * @Description : Verifies caller-controlled request headers cannot impersonate authenticated audit actors.
 *
 */
package dz.sh.hidra.platform.web;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.platform.observability.LoggingContext;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class HidraRequestContextFilterTest {

    @AfterEach
    void clearContext() {
        LoggingContext.clearAll();
        MDC.clear();
    }

    @Test
    void callerSuppliedActorHeaderDoesNotPopulateAuthenticatedActorContext() throws Exception {
        HidraRequestContextFilter filter = new HidraRequestContextFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/topology/pipelines");
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("X-Actor-Id", "spoofed-administrator");
        request.addHeader(PlatformHeaders.CORRELATION_ID, "corr-1");
        request.addHeader(PlatformHeaders.REQUEST_ID, "req-1");

        FilterChain chain = (servletRequest, servletResponse) -> {
            assertThat(LoggingContext.get(LoggingContext.ACTOR_ID)).isEmpty();
            assertThat(MDC.get(LoggingContext.ACTOR_ID)).isNull();
            assertThat(LoggingContext.get(LoggingContext.CORRELATION_ID)).contains("corr-1");
            assertThat(LoggingContext.get(LoggingContext.REQUEST_ID)).contains("req-1");
        };

        filter.doFilter(request, response, chain);

        assertThat(response.getHeader(PlatformHeaders.CORRELATION_ID)).isEqualTo("corr-1");
        assertThat(response.getHeader(PlatformHeaders.REQUEST_ID)).isEqualTo("req-1");
        assertThat(LoggingContext.get(LoggingContext.ACTOR_ID)).isEmpty();
        assertThat(MDC.get(LoggingContext.ACTOR_ID)).isNull();
    }

    @Test
    void platformHeaderContractNoLongerPublishesActorIdentityHeader() {
        assertThat(PlatformHeaders.class.getDeclaredFields())
                .extracting(field -> field.getName())
                .doesNotContain("ACTOR_ID");
    }
}
