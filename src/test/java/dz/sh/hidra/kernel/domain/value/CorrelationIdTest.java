/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrelationIdTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Verifies correlation ID creation and invalid input rejection.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CorrelationIdTest {

    @Test
    void shouldTrimProvidedValue() {
        CorrelationId correlationId = CorrelationId.of("  correlation-1  ");

        assertThat(correlationId.value()).isEqualTo("correlation-1");
    }

    @Test
    void shouldCreateGeneratedId() {
        CorrelationId correlationId = CorrelationId.newId();

        assertThat(correlationId.value()).isNotBlank();
    }

    @Test
    void shouldRejectBlankValue() {
        assertThatThrownBy(() -> CorrelationId.of("  "))
                .isInstanceOf(InvalidValueObjectException.class)
                .hasMessage("CorrelationId must not be blank.");
    }
}
