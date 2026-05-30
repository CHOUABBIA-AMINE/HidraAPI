/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApiErrorResponseTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.api.error
 *
 * @Description : Verifies API error response shape.
 *
 */
package dz.sh.hidra.kernel.api.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ApiErrorResponseTest {

    @Test
    void shouldCreateErrorResponseWithCorrelationId() {
        ApiErrorResponse response = ApiErrorResponse.of(400, ApiErrorCode.VALIDATION_ERROR, "Invalid.", "/api", " correlation-1 ");

        assertThat(response.timestamp()).isNotNull();
        assertThat(response.status()).isEqualTo(400);
        assertThat(response.correlationId()).isEqualTo("correlation-1");
        assertThat(response.hasDetails()).isFalse();
    }

    @Test
    void shouldDefensivelyCopyDetails() {
        List<ValidationErrorDetail> details = new ArrayList<>();
        details.add(new ValidationErrorDetail("field", "message", "bad"));

        ApiErrorResponse response = ApiErrorResponse.withDetails(400, ApiErrorCode.VALIDATION_ERROR, "Invalid.", "/api", "c1", details);
        details.add(new ValidationErrorDetail("other", "message", "bad"));

        assertThat(response.details()).hasSize(1);
        assertThatThrownBy(() -> response.details().add(new ValidationErrorDetail("x", "y", "z")))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldRejectNullTimestamp() {
        assertThatThrownBy(() -> new ApiErrorResponse(null, 500, ApiErrorCode.INTERNAL_ERROR, "Error.", "/api", "c1", List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("API error timestamp must not be null.");
    }
}
