/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyOperationAuthenticationQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Resolves bounded ambient native or after-commit authentication through private origin and fresh Identity state.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyOperationAuthenticationContract;
import dz.sh.hidra.modules.identity.application.port.out.AccessTokenIssuerPort;
import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.application.service.OperationAuthenticationQueryApplicationService;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import tools.jackson.core.StreamReadConstraints;
import tools.jackson.core.StreamReadFeature;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** Point-in-time B1; no permission, original assertion, callback or owner fence. */
@Component
public final class TopologyOperationAuthenticationQueryAdapter
        implements TopologyOperationAuthenticationContract {
    private static final JsonMapper JSON =
            JsonMapper.builder(
                            JsonFactory.builder()
                                    .streamReadConstraints(
                                            StreamReadConstraints.builder()
                                                    .maxNestingDepth(32)
                                                    .build())
                                    .enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION)
                                    .build())
                    .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                    .build();
    private final OperationAuthenticationBindingRepositoryPort bindings;
    private final OperationAuthenticationQueryApplicationService owner;
    private final ObjectProvider<JwtDecoder> decoder;
    private final AccessTokenIssuerPort issuer;
    private final TrustedAuthenticationVerificationRegistry registry;
    private final Clock clock;
    private final int maxBytes;

    public TopologyOperationAuthenticationQueryAdapter(
            OperationAuthenticationBindingRepositoryPort bindings,
            OperationAuthenticationQueryApplicationService owner,
            @Qualifier("hidraJwtDecoder") ObjectProvider<JwtDecoder> decoder,
            AccessTokenIssuerPort issuer,
            TrustedAuthenticationVerificationRegistry registry,
            @Qualifier("identityAuthenticationClock") Clock clock,
            @Value("${hidra.identity.bound-authentication.max-token-bytes:65536}") int maxBytes) {
        this.bindings = Objects.requireNonNull(bindings);
        this.owner = Objects.requireNonNull(owner);
        this.decoder = Objects.requireNonNull(decoder);
        this.issuer = Objects.requireNonNull(issuer);
        this.registry = Objects.requireNonNull(registry);
        this.clock = Objects.requireNonNull(clock);
        if (maxBytes <= 0) throw new IllegalArgumentException("Invalid native byte envelope.");
        this.maxBytes = maxBytes;
    }

    @Override
    public Result queryCurrentBoundAuthentication() {
        var actual = SecurityContextHolder.getContext().getAuthentication();
        if (actual == null || !actual.isAuthenticated()) return Result.denied();
        var settings = issuer.settings();
        OperationAuthenticationBinding b;
        if (actual instanceof JwtAuthenticationToken bearer) {
            String raw = bearer.getToken().getTokenValue();
            if (raw == null
                    || raw.isBlank()
                    || raw.getBytes(StandardCharsets.UTF_8).length > maxBytes)
                return Result.denied();
            Jwt verified;
            try {
                verified = decoder.getObject().decode(raw);
            } catch (BadJwtException invalid) {
                return Result.denied();
            } catch (RuntimeException unavailable) {
                throw new IllegalStateException(
                        "Native authentication verification is unavailable.");
            }
            if (verified == null || !raw.equals(verified.getTokenValue()))
                throw new IllegalStateException("Native decoder provenance is corrupt.");
            var claims = rawClaims(raw);
            if (claims == null
                    || !settings.issuer().equals(claims.issuer())
                    || !settings.audience().equals(claims.audience())) return Result.denied();
            b = bindings.findByNativeIssuerAndJti(claims.issuer(), claims.jti()).orElse(null);
            if (b == null) return Result.denied();
            registry.validatePersistentOrigin(b);
            if (!claims.matches(b)
                    || !HidraAccessTokenIssuer.exactClaims(
                            verified,
                            b.nativeIssuer(),
                            b.nativeAudience(),
                            b.userId(),
                            b.jti(),
                            b.authenticationType(),
                            b.providerId(),
                            b.issuedAt(),
                            b.expiresAt())) return Result.denied();
        } else {
            var receipt = registry.completed(actual);
            if (receipt == null) return Result.denied();
            b =
                    bindings.findByNativeIssuerAndJti(receipt.nativeIssuer(), receipt.jti())
                            .orElse(null);
            if (b == null) return Result.denied();
            registry.validatePersistentOrigin(b);
            if (!b.equals(receipt))
                throw new IllegalStateException("Committed authentication provenance is corrupt.");
        }
        if (!settings.issuer().equals(b.nativeIssuer())
                || !settings.audience().equals(b.nativeAudience())) return Result.denied();
        var result = owner.query(b, clock.instant());
        Instant after = clock.instant();
        if (after.isBefore(b.issuedAt())
                || !after.isBefore(b.expiresAt())
                || (b.sourceExpiresAt() != null && !after.isBefore(b.sourceExpiresAt())))
            return Result.denied();
        return result;
    }

    private record RawClaims(
            String issuer,
            String audience,
            String subject,
            String jti,
            String type,
            String provider,
            Instant issued,
            Instant expires) {
        boolean matches(OperationAuthenticationBinding b) {
            return issuer.equals(b.nativeIssuer())
                    && audience.equals(b.nativeAudience())
                    && subject.equals(b.userId())
                    && jti.equals(b.jti())
                    && type.equals(b.authenticationType())
                    && provider.equals(b.providerId())
                    && issued.equals(b.issuedAt())
                    && expires.equals(b.expiresAt());
        }
    }

    /** Parse only the signature-validated original wire, retaining raw JSON types/duplicates. */
    private static RawClaims rawClaims(String raw) {
        try {
            String[] parts = raw.split("\\.", -1);
            if (parts.length != 3) return null;
            for (String part : parts) if (!part.matches("[A-Za-z0-9_-]+")) return null;
            JsonNode header = JSON.readTree(Base64.getUrlDecoder().decode(parts[0]));
            JsonNode claims = JSON.readTree(Base64.getUrlDecoder().decode(parts[1]));
            if (!header.isObject() || !"HS256".equals(text(header, "alg")) || !claims.isObject())
                return null;
            String issuer = text(claims, "iss"),
                    subject = text(claims, "sub"),
                    jti = text(claims, "jti"),
                    type = text(claims, "authentication_type"),
                    provider = text(claims, "identity_provider_id");
            var aud = claims.get("aud");
            String audience;
            // The existing Nimbus issuer emits the RFC7519 singleton string form. Preserve it,
            // while also accepting exactly one textual array member; never coerce numbers/objects.
            if (aud != null && aud.isString()) audience = aud.stringValue();
            else if (aud != null && aud.isArray() && aud.size() == 1 && aud.get(0).isString())
                audience = aud.get(0).stringValue();
            else return null;
            new dz.sh.hidra.modules.identity.application.model.AccessTokenIssuerSettings(
                    issuer, audience, 1);
            if (!ownerId(subject)
                    || !ownerId(provider)
                    || !canonical(jti)
                    || !List.of("LOCAL", "LDAP", "ACTIVE_DIRECTORY", "OIDC").contains(type))
                return null;
            Instant issued = time(claims.get("iat")), expires = time(claims.get("exp"));
            if (issued == null || expires == null || !expires.isAfter(issued)) return null;
            return new RawClaims(issuer, audience, subject, jti, type, provider, issued, expires);
        } catch (RuntimeException invalid) {
            return null;
        }
    }

    private static String text(JsonNode node, String name) {
        var value = node.get(name);
        return value != null && value.isString() ? value.stringValue() : null;
    }

    private static Instant time(JsonNode n) {
        if (n == null || !n.isIntegralNumber() || !n.canConvertToLong()) return null;
        long seconds = n.longValue();
        return seconds >= -210866803200L && seconds < 9224318016000L
                ? Instant.ofEpochSecond(seconds)
                : null;
    }

    private static boolean ownerId(String value) {
        if (value == null
                || value.isBlank()
                || !value.equals(value.trim())
                || !value.equals(value.strip())
                || value.codePointCount(0, value.length()) > 80) return false;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == 0) return false;
            if (Character.isHighSurrogate(c)) {
                if (++i >= value.length() || !Character.isLowSurrogate(value.charAt(i)))
                    return false;
            } else if (Character.isLowSurrogate(c)) return false;
        }
        return true;
    }

    private static boolean canonical(String value) {
        try {
            return value != null && UUID.fromString(value).toString().equals(value);
        } catch (IllegalArgumentException invalid) {
            return false;
        }
    }
}
