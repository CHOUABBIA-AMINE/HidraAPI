/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraUnifiedJwtCompatibilityTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Verifies provider-neutral Hidra JWT issuance remains compatible with protected-API decoding and authority reconstruction.
 *
 */
package dz.sh.hidra.platform.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.identity.application.model.IssuedAccessToken;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.infrastructure.security.HidraAccessTokenIssuer;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

class HidraUnifiedJwtCompatibilityTest {

    private static final String HMAC_SECRET = "0123456789abcdef0123456789abcdef";
    private static final String ISSUER = "hidra-api";
    private static final String AUDIENCE = "hidra-api";
    private static final String USER_ID = "user-unified-jwt";

    @ParameterizedTest
    @MethodSource("authenticationSources")
    void allAuthenticationSourcesIssueTokensAcceptedByProtectedApiSecurity(
            ProviderType authenticationType,
            String identityProviderId
    ) {
        HidraJwtEncoderConfiguration encoderConfiguration = new HidraJwtEncoderConfiguration();
        HidraJwtDecoderConfiguration decoderConfiguration = new HidraJwtDecoderConfiguration();
        HidraSecurityConfiguration securityConfiguration = new HidraSecurityConfiguration();

        JwtEncoder encoder = encoderConfiguration.hidraJwtEncoder(HMAC_SECRET);
        JwtDecoder decoder = decoderConfiguration.hidraJwtDecoder(HMAC_SECRET, ISSUER, AUDIENCE);
        var authenticationConverter = securityConfiguration.hidraJwtAuthenticationConverter(
                "sub", "roles", "scope", "ROLE_"
        );
        HidraAccessTokenIssuer issuer = new HidraAccessTokenIssuer(
                encoder, ISSUER, AUDIENCE, "sub", "roles", "scope", 900
        );

        HidraPrincipal principal = new HidraPrincipal(
                USER_ID,
                "pipeline.operator",
                "Pipeline Operator",
                authenticationType,
                identityProviderId,
                Set.of("OPERATOR", "DISPATCHER"),
                Set.of("pipeline:read", "alarm:acknowledge")
        );

        IssuedAccessToken issued = issuer.issue(principal);
        Jwt decoded = decoder.decode(issued.tokenValue());
        Authentication authentication = authenticationConverter.convert(decoded);

        assertThat(issued.tokenType()).isEqualTo("Bearer");
        assertThat(decoded.getClaimAsString("iss")).isEqualTo(ISSUER);
        assertThat(decoded.getAudience()).containsExactly(AUDIENCE);
        assertThat(decoded.getSubject()).isEqualTo(USER_ID);
        assertThat(decoded.getId()).isEqualTo(issued.jti());
        assertThat(decoded.getClaimAsString("username")).isEqualTo("pipeline.operator");
        assertThat(decoded.getClaimAsString("authentication_type")).isEqualTo(authenticationType.name());
        if (identityProviderId == null) {
            assertThat(decoded.hasClaim("identity_provider_id")).isFalse();
        } else {
            assertThat(decoded.getClaimAsString("identity_provider_id")).isEqualTo(identityProviderId);
        }
        assertThat(decoded.getClaimAsStringList("roles")).containsExactly("DISPATCHER", "OPERATOR");
        assertThat(decoded.getClaimAsStringList("scope")).containsExactly("alarm:acknowledge", "pipeline:read");

        assertThat(authentication).isNotNull();
        assertThat(authentication.isAuthenticated()).isTrue();
        assertThat(authentication.getName()).isEqualTo(USER_ID);
        assertThat(authentication.getAuthorities())
                .extracting(authority -> authority.getAuthority())
                .containsExactlyInAnyOrder(
                        "ROLE_OPERATOR",
                        "ROLE_DISPATCHER",
                        "SCOPE_pipeline:read",
                        "SCOPE_alarm:acknowledge"
                );
    }

    static Stream<Arguments> authenticationSources() {
        return Stream.of(
                Arguments.of(ProviderType.LOCAL, null),
                Arguments.of(ProviderType.LDAP, "provider-ldap"),
                Arguments.of(ProviderType.ACTIVE_DIRECTORY, "provider-ad"),
                Arguments.of(ProviderType.OIDC, "provider-oidc")
        );
    }
}
