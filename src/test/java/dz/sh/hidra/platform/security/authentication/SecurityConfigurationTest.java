/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SecurityConfigurationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security.authentication
 *
 * @Description : Verifies basic security configuration behavior.
 *
 */
package dz.sh.hidra.platform.security.authentication;

import dz.sh.hidra.platform.configuration.HidraPlatformProperties;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityConfigurationTest {

    @Test
    void shouldCreateCorsConfigurationFromPlatformProperties() {
        SecurityConfiguration configuration = new SecurityConfiguration();
        HidraPlatformProperties properties = new HidraPlatformProperties(null, null, null, null, null);

        var source = configuration.corsConfigurationSource(properties);
        var cors = source.getCorsConfiguration(new MockHttpServletRequest("GET", "/api/test"));

        assertThat(cors).isNotNull();
        assertThat(cors.getAllowedMethods()).contains("GET", "POST", "OPTIONS");
        assertThat(cors.getAllowedHeaders()).contains("Authorization", "X-Correlation-Id");
    }
}
