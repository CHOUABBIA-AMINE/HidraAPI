/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RequestIdTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Verifies request ID creation and invalid input rejection.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestIdTest {

    @Test
    void shouldTrimProvidedValue() {
        RequestId requestId = RequestId.of("  request-1  ");

        assertThat(requestId.value()).isEqualTo("request-1");
    }

    @Test
    void shouldCreateGeneratedId() {
        RequestId requestId = RequestId.newId();

        assertThat(requestId.value()).isNotBlank();
    }

    @Test
    void shouldRejectNullValue() {
        assertThatThrownBy(() -> RequestId.of(null))
                .isInstanceOf(InvalidValueObjectException.class)
                .hasMessage("RequestId must not be blank.");
    }
}
