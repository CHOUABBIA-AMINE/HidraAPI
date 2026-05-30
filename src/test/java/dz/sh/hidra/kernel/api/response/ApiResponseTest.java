/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApiResponseTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.api.response
 *
 * @Description : Verifies API response shape.
 *
 */
package dz.sh.hidra.kernel.api.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApiResponseTest {

    @Test
    void shouldCreateResponseWithCorrelationId() {
        ApiResponse<String> response = ApiResponse.of("data", " Created. ", " correlation-1 ");

        assertThat(response.data()).isEqualTo("data");
        assertThat(response.message()).isEqualTo("Created.");
        assertThat(response.correlationId()).isEqualTo("correlation-1");
        assertThat(response.timestamp()).isNotNull();
    }

    @Test
    void shouldRejectNullTimestamp() {
        assertThatThrownBy(() -> new ApiResponse<>("data", "message", "c1", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("API response timestamp must not be null.");
    }
}
