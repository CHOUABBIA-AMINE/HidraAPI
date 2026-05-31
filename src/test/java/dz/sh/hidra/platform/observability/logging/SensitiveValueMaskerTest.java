/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SensitiveValueMaskerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.logging
 *
 * @Description : Verifies sensitive value masking.
 *
 */
package dz.sh.hidra.platform.observability.logging;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SensitiveValueMaskerTest {

    @Test
    void shouldMaskSensitiveKeysDeterministically() {
        assertThat(SensitiveValueMasker.mask("api-token", "secret-value")).isEqualTo(SensitiveValueMasker.MASKED_VALUE);
        assertThat(SensitiveValueMasker.mask("Authorization", "Bearer token")).isEqualTo(SensitiveValueMasker.MASKED_VALUE);
    }

    @Test
    void shouldKeepSafeAndNullValues() {
        assertThat(SensitiveValueMasker.mask("module", "platform")).isEqualTo("platform");
        assertThat(SensitiveValueMasker.mask("password", null)).isNull();
    }
}
