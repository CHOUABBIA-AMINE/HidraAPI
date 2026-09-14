/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraSecurityConfigurationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Verifies browser-visible CORS response-header exposure owned by platform security configuration.
 *
 */
package dz.sh.hidra.platform.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

class HidraSecurityConfigurationTest {

    @Test
    void exposesDocumentDownloadEvidenceHeadersForCrossOriginBrowserClients() {
        UrlBasedCorsConfigurationSource source = HidraSecurityConfiguration.buildCorsConfigurationSource(
                "http://localhost:5173",
                "GET,POST,OPTIONS",
                "Authorization,Content-Type",
                "X-Correlation-Id,X-Request-Id,Content-Disposition,Content-Length,Accept-Ranges"
        );

        CorsConfiguration configuration = source.getCorsConfigurations().get("/**");

        assertThat(configuration).isNotNull();
        assertThat(configuration.getExposedHeaders()).containsExactly(
                "X-Correlation-Id",
                "X-Request-Id",
                "Content-Disposition",
                "Content-Length",
                "Accept-Ranges"
        );
        assertThat(configuration.getAllowedOrigins()).isEqualTo(List.of("http://localhost:5173"));
    }
}
