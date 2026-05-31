/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApiErrorFactoryTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.exception
 *
 * @Description : Verifies API error response creation.
 *
 */
package dz.sh.hidra.platform.exception;

import dz.sh.hidra.kernel.api.error.ApiErrorCode;
import dz.sh.hidra.kernel.domain.exception.DomainException;
import dz.sh.hidra.kernel.domain.value.CorrelationId;
import dz.sh.hidra.kernel.domain.value.RequestId;
import dz.sh.hidra.platform.observability.correlation.CorrelationContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;

class ApiErrorFactoryTest {

    private final ApiErrorFactory factory = new ApiErrorFactory();

    @AfterEach
    void clearContext() {
        CorrelationContext.clear();
    }

    @Test
    void shouldIncludeCorrelationIdInDomainErrors() {
        CorrelationContext.set(new CorrelationContext(CorrelationId.of("correlation-1"), RequestId.of("request-1")));

        var response = factory.domainError(new DomainException("Invalid operation."), "/api/test");

        assertThat(response.error()).isEqualTo(ApiErrorCode.DOMAIN_ERROR);
        assertThat(response.message()).isEqualTo("Invalid operation.");
        assertThat(response.correlationId()).isEqualTo("correlation-1");
    }

    @Test
    void shouldMapAuthenticationErrors() {
        var response = factory.authenticationError(new BadCredentialsException("bad"), "/api/test");

        assertThat(response.status()).isEqualTo(401);
        assertThat(response.error()).isEqualTo(ApiErrorCode.AUTHENTICATION_ERROR);
    }
}
