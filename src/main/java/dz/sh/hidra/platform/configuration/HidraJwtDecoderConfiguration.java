/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraJwtDecoderConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Configures JWT validation for external resource tokens and Hidra-issued HMAC access tokens.
 *
 */
package dz.sh.hidra.platform.configuration;

import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

/**
 * JWT decoder configuration for HidraAPI resource-server mode.
 */
@Configuration(proxyBeanMethods = false)
public class HidraJwtDecoderConfiguration {

    @Bean
    JwtDecoder hidraJwtDecoder(
            @Value("${hidra.platform.security.jwt.issuer-uri:}") String issuerUri,
            @Value("${hidra.platform.security.jwt.jwk-set-uri:}") String jwkSetUri,
            @Value("${hidra.platform.security.jwt.hmac-secret:}") String hmacSecret,
            @Value("${hidra.platform.security.jwt.token-issuer:hidra-api}") String tokenIssuer,
            @Value("${hidra.platform.security.jwt.audience:}") String audience
    ) {
        String normalizedIssuerUri = normalize(issuerUri, null);
        String normalizedJwkSetUri = normalize(jwkSetUri, null);
        String normalizedHmacSecret = normalize(hmacSecret, null);
        String normalizedTokenIssuer = normalize(tokenIssuer, "hidra-api");

        NimbusJwtDecoder decoder;
        String validationIssuer;
        if (normalizedJwkSetUri != null) {
            decoder = NimbusJwtDecoder.withJwkSetUri(normalizedJwkSetUri).build();
            validationIssuer = normalizedIssuerUri;
        } else if (normalizedIssuerUri != null) {
            decoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(normalizedIssuerUri);
            validationIssuer = normalizedIssuerUri;
        } else if (normalizedHmacSecret != null) {
            decoder = NimbusJwtDecoder.withSecretKey(new SecretKeySpec(
                    normalizedHmacSecret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            )).macAlgorithm(MacAlgorithm.HS256).build();
            validationIssuer = normalizedTokenIssuer;
        } else {
            throw new IllegalStateException(
                    "JWT authentication mode requires HIDRA_JWT_ISSUER_URI, HIDRA_JWT_JWK_SET_URI, or HIDRA_JWT_HMAC_SECRET."
            );
        }

        decoder.setJwtValidator(jwtValidator(validationIssuer, normalize(audience, null)));
        return decoder;
    }

    private static OAuth2TokenValidator<Jwt> jwtValidator(String issuer, String audience) {
        OAuth2TokenValidator<Jwt> defaultValidator = issuer == null
                ? JwtValidators.createDefault()
                : JwtValidators.createDefaultWithIssuer(issuer);
        if (audience == null) {
            return defaultValidator;
        }
        OAuth2TokenValidator<Jwt> audienceValidator = new JwtClaimValidator<List<String>>(
                "aud",
                audiences -> audiences != null && audiences.contains(audience)
        );
        return new DelegatingOAuth2TokenValidator<>(defaultValidator, audienceValidator);
    }

    private static String normalize(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }
}
