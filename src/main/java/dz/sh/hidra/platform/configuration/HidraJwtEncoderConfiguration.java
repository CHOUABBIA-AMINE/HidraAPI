/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraJwtEncoderConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Configures the JWT encoder used to sign Hidra-issued access tokens with externalized HMAC material.
 *
 */
package dz.sh.hidra.platform.configuration;

import java.nio.charset.StandardCharsets;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

/**
 * Technical signing configuration for Hidra access tokens.
 */
@Configuration(proxyBeanMethods = false)
public class HidraJwtEncoderConfiguration {

    private static final int MINIMUM_HS256_SECRET_BYTES = 32;

    @Bean
    @Lazy
    JwtEncoder hidraJwtEncoder(
            @Value("${hidra.platform.security.jwt.hmac-secret:}") String hmacSecret
    ) {
        String normalizedSecret = normalize(hmacSecret);
        if (normalizedSecret == null) {
            throw new IllegalStateException(
                    "Hidra access-token issuance requires externalized HIDRA_JWT_HMAC_SECRET signing material."
            );
        }

        byte[] secretBytes = normalizedSecret.getBytes(StandardCharsets.UTF_8);
        if (secretBytes.length < MINIMUM_HS256_SECRET_BYTES) {
            throw new IllegalStateException("HIDRA_JWT_HMAC_SECRET must contain at least 32 UTF-8 bytes for HS256.");
        }

        return NimbusJwtEncoder.withSecretKey(new SecretKeySpec(secretBytes, "HmacSHA256"))
                .algorithm(MacAlgorithm.HS256)
                .build();
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
