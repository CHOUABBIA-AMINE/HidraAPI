/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrelationContextTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.correlation
 *
 * @Description : Verifies correlation context lifecycle.
 *
 */
package dz.sh.hidra.platform.observability.correlation;

import dz.sh.hidra.kernel.domain.value.CorrelationId;
import dz.sh.hidra.kernel.domain.value.RequestId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CorrelationContextTest {

    @AfterEach
    void clear() {
        CorrelationContext.clear();
    }

    @Test
    void shouldStoreAndClearCurrentContext() {
        CorrelationContext context = new CorrelationContext(CorrelationId.of("correlation-1"), RequestId.of("request-1"));

        CorrelationContext.set(context);

        assertThat(CorrelationContext.current()).contains(context);
        CorrelationContext.clear();
        assertThat(CorrelationContext.current()).isEmpty();
    }
}
