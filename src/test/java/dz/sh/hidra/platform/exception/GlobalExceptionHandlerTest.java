/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GlobalExceptionHandlerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.exception
 *
 * @Description : Verifies global exception mapping behavior.
 *
 */
package dz.sh.hidra.platform.exception;

import dz.sh.hidra.kernel.api.error.ApiErrorCode;
import dz.sh.hidra.kernel.domain.exception.DomainException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler(new ApiErrorFactory());

    @Test
    void shouldMapDomainExceptionToKernelErrorResponse() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/domain");

        var response = handler.handleDomainException(new DomainException("Broken rule."), request);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().error()).isEqualTo(ApiErrorCode.DOMAIN_ERROR);
        assertThat(response.getBody().path()).isEqualTo("/api/domain");
    }
}
