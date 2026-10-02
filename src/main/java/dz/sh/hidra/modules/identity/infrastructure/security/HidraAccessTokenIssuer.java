/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraAccessTokenIssuer
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Issues one Hidra JWT access-token schema from normalized Hidra principals regardless of authentication provider.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.model.IssuedAccessToken;
import dz.sh.hidra.modules.identity.application.port.out.AccessTokenIssuerPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

/**
 * Provider-neutral Hidra access-token issuer.
 *
 * <p>Only Hidra-owned principal data is promoted into authorization claims. External
 * provider claims and directory groups never enter the token schema here.</p>
 */
@Component
@Lazy
public final class HidraAccessTokenIssuer implements AccessTokenIssuerPort {

    private static final String TOKEN_TYPE = "Bearer";
    private static final String SUBJECT_CLAIM = "sub";

    private final JwtEncoder jwtEncoder;
    private final String issuer;
    private final String audience;
    private final String principalClaim;
    private final String rolesClaim;
    private final String scopeClaim;
    private final long accessTokenTtlSeconds;

    public HidraAccessTokenIssuer(
            JwtEncoder jwtEncoder,
            @Value("${hidra.platform.security.jwt.token-issuer:hidra-api}") String issuer,
            @Value("${hidra.platform.security.jwt.audience:hidra-api}") String audience,
            @Value("${hidra.platform.security.jwt.principal-claim:sub}") String principalClaim,
            @Value("${hidra.platform.security.jwt.roles-claim:roles}") String rolesClaim,
            @Value("${hidra.platform.security.jwt.scope-claim:scope}") String scopeClaim,
            @Value("${hidra.platform.security.jwt.access-token-ttl-seconds:900}") long accessTokenTtlSeconds
    ) {
        this.jwtEncoder = Objects.requireNonNull(jwtEncoder);
        this.issuer = requireConfigured(issuer, "hidra.platform.security.jwt.token-issuer");
        this.audience = requireConfigured(audience, "hidra.platform.security.jwt.audience");
        this.principalClaim = requireConfigured(principalClaim, "hidra.platform.security.jwt.principal-claim");
        this.rolesClaim = requireConfigured(rolesClaim, "hidra.platform.security.jwt.roles-claim");
        this.scopeClaim = requireConfigured(scopeClaim, "hidra.platform.security.jwt.scope-claim");
        if (accessTokenTtlSeconds <= 0) {
            throw new IllegalStateException("Hidra JWT access-token TTL must be greater than zero seconds.");
        }
        this.accessTokenTtlSeconds = accessTokenTtlSeconds;
    }

    /**
     * Issues the common Hidra bearer token for an already authenticated Hidra principal.
     */
    @Override
    public IssuedAccessToken issue(HidraPrincipal principal) {
        Objects.requireNonNull(principal, "Hidra principal must not be null.");

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(accessTokenTtlSeconds);
        String jti = UUID.randomUUID().toString();

        List<String> roles = new ArrayList<>(new TreeSet<>(principal.roles()));
        List<String> permissions = new ArrayList<>(new TreeSet<>(principal.permissions()));

        JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(principal.userId())
                .audience(List.of(audience))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .id(jti)
                .claim(rolesClaim, roles)
                .claim(scopeClaim, permissions)
                .claim("username", principal.username())
                .claim("authentication_type", principal.authenticationType().name());

        if (!SUBJECT_CLAIM.equals(principalClaim)) {
            claims.claim(principalClaim, principal.userId());
        }
        if (principal.displayName() != null && !principal.displayName().isBlank()) {
            claims.claim("display_name", principal.displayName());
        }
        if (principal.identityProviderId() != null && !principal.identityProviderId().isBlank()) {
            claims.claim("identity_provider_id", principal.identityProviderId());
        }

        Jwt encoded = jwtEncoder.encode(JwtEncoderParameters.from(claims.build()));
        return new IssuedAccessToken(encoded.getTokenValue(), TOKEN_TYPE, jti, issuedAt, expiresAt);
    }

    private static String requireConfigured(String value, String propertyName) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(propertyName + " must not be blank for Hidra token issuance.");
        }
        return value.trim();
    }
}
