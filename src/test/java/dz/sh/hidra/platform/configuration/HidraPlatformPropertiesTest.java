/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraPlatformPropertiesTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Verifies platform properties defaults and typed grouping.
 *
 */
package dz.sh.hidra.platform.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

class HidraPlatformPropertiesTest {

    @Test
    void shouldProvideDefaultGroupedPlatformProperties() {
        HidraPlatformProperties properties = new HidraPlatformProperties(null, null, null, null, null);

        assertThat(properties.observability().correlation().headerName()).isEqualTo("X-Correlation-Id");
        assertThat(properties.security().cors().allowedMethods()).containsExactly("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
        assertThat(properties.persistence().auditing().enabled()).isTrue();
        assertThat(properties.events().outbox().tableName()).isEqualTo("hidra_platform_outbox_event");
        assertThat(properties.tenancy().defaultScope()).isEqualTo("SH");
    }

    @Test
    void shouldKeepCorsListsImmutable() {
        HidraPlatformProperties.Cors cors = new HidraPlatformProperties.Cors(true, List.of("https://hidra.example"), null, null, null);

        assertThat(cors.allowedOrigins()).containsExactly("https://hidra.example");
        assertThatThrownBy(() -> cors.allowedOrigins().add("https://other.example"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
