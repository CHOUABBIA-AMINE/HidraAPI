/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationAuthenticationBinding
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Validates immutable provenance and defensive producer authentication evidence for one native bearer/session binding.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/** Immutable format-one operation authentication association; not an authorization decision. */
public record OperationAuthenticationBinding(
        String bindingId,
        short formatVersion,
        String nativeIssuer,
        String nativeAudience,
        String jti,
        String sessionId,
        String userId,
        String authenticationType,
        String sessionProtocol,
        String providerId,
        long providerSecurityVersion,
        String externalIdentityId,
        String externalSubject,
        Long externalSecurityVersion,
        String sourceIssuer,
        Instant sourceExpiresAt,
        String credentialId,
        Long credentialSecurityVersion,
        Instant issuedAt,
        Instant expiresAt,
        byte[] producerAuthenticator) {
    public OperationAuthenticationBinding {
        canonicalUuid(bindingId, "bindingId");
        if (formatVersion != 1) throw invalid("formatVersion");
        boundedExact(nativeIssuer, "nativeIssuer", 512);
        boundedExact(nativeAudience, "nativeAudience", 512);
        canonicalUuid(jti, "jti");
        canonicalUuid(sessionId, "sessionId");
        ownerId(userId, "userId");
        ownerId(providerId, "providerId");
        if (providerSecurityVersion <= 0) throw invalid("providerSecurityVersion");
        if (!finite(issuedAt)
                || !finite(expiresAt)
                || issuedAt.getNano() != 0
                || expiresAt.getNano() != 0
                || !issuedAt.isBefore(expiresAt)) {
            throw invalid("native interval");
        }
        if (authenticationType == null || sessionProtocol == null) throw invalid("type/protocol");
        String type = authenticationType;
        String protocol = sessionProtocol;
        switch (type) {
            case "LOCAL" -> {
                if (!"LOCAL".equals(protocol)
                        || externalIdentityId != null
                        || externalSubject != null
                        || externalSecurityVersion != null
                        || sourceIssuer != null
                        || sourceExpiresAt != null
                        || credentialSecurityVersion == null
                        || credentialSecurityVersion <= 0) {
                    throw invalid("LOCAL provenance");
                }
                ownerId(credentialId, "credentialId");
            }
            case "LDAP", "ACTIVE_DIRECTORY" -> {
                if (!"LDAP".equals(protocol)
                        || credentialId != null
                        || credentialSecurityVersion != null
                        || sourceIssuer != null
                        || sourceExpiresAt != null) throw invalid("directory provenance");
                requireExternal(externalIdentityId, externalSubject, externalSecurityVersion);
            }
            case "OIDC" -> {
                if (!"OIDC".equals(protocol)
                        || credentialId != null
                        || credentialSecurityVersion != null
                        || !finite(sourceExpiresAt)
                        || sourceExpiresAt.getNano() % 1000 != 0
                        || !expiresAt.isAfter(issuedAt)
                        || expiresAt.isAfter(sourceExpiresAt)) throw invalid("OIDC provenance");
                requireExternal(externalIdentityId, externalSubject, externalSecurityVersion);
                sourceText(sourceIssuer, "sourceIssuer");
            }
            default -> throw invalid("authenticationType");
        }
        if (producerAuthenticator == null || producerAuthenticator.length != 32) {
            throw invalid("producerAuthenticator");
        }
        producerAuthenticator = producerAuthenticator.clone();
    }

    @Override
    public byte[] producerAuthenticator() {
        return producerAuthenticator.clone();
    }

    /** Record equality cannot use array identity for immutable tag bytes. */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof OperationAuthenticationBinding b)) return false;
        return formatVersion == b.formatVersion
                && providerSecurityVersion == b.providerSecurityVersion
                && Objects.equals(bindingId, b.bindingId)
                && Objects.equals(nativeIssuer, b.nativeIssuer)
                && Objects.equals(nativeAudience, b.nativeAudience)
                && Objects.equals(jti, b.jti)
                && Objects.equals(sessionId, b.sessionId)
                && Objects.equals(userId, b.userId)
                && Objects.equals(authenticationType, b.authenticationType)
                && Objects.equals(sessionProtocol, b.sessionProtocol)
                && Objects.equals(providerId, b.providerId)
                && Objects.equals(externalIdentityId, b.externalIdentityId)
                && Objects.equals(externalSubject, b.externalSubject)
                && Objects.equals(externalSecurityVersion, b.externalSecurityVersion)
                && Objects.equals(sourceIssuer, b.sourceIssuer)
                && Objects.equals(sourceExpiresAt, b.sourceExpiresAt)
                && Objects.equals(credentialId, b.credentialId)
                && Objects.equals(credentialSecurityVersion, b.credentialSecurityVersion)
                && Objects.equals(issuedAt, b.issuedAt)
                && Objects.equals(expiresAt, b.expiresAt)
                && Arrays.equals(producerAuthenticator, b.producerAuthenticator);
    }

    @Override
    public int hashCode() {
        int hash =
                Objects.hash(
                        bindingId,
                        formatVersion,
                        nativeIssuer,
                        nativeAudience,
                        jti,
                        sessionId,
                        userId,
                        authenticationType,
                        sessionProtocol,
                        providerId,
                        providerSecurityVersion,
                        externalIdentityId,
                        externalSubject,
                        externalSecurityVersion,
                        sourceIssuer,
                        sourceExpiresAt,
                        credentialId,
                        credentialSecurityVersion,
                        issuedAt,
                        expiresAt);
        return 31 * hash + Arrays.hashCode(producerAuthenticator);
    }

    /** Do not leak the producer attestation through diagnostics or record rendering. */
    @Override
    public String toString() {
        return "OperationAuthenticationBinding[formatVersion="
                + formatVersion
                + ", producerAuthenticator=<redacted>]";
    }

    private static void requireExternal(String id, String subject, Long version) {
        ownerId(id, "externalIdentityId");
        sourceText(subject, "externalSubject");
        if (version == null || version <= 0) throw invalid("externalSecurityVersion");
    }

    private static void canonicalUuid(String text, String field) {
        boundedExact(text, field, 36);
        try {
            if (!UUID.fromString(text).toString().equals(text)) throw invalid(field);
        } catch (IllegalArgumentException exception) {
            throw invalid(field);
        }
    }

    private static boolean finite(Instant value) {
        // PostgreSQL finite timestamptz bounds; retain original source nanoseconds.
        return value != null
                && value.getEpochSecond() >= -210866803200L
                && value.getEpochSecond() < 9224318016000L;
    }

    private static void sourceText(String text, String field) {
        if (text == null || text.isBlank()) throw invalid(field);
        strictUnicode(text, field);
    }

    private static void strictUnicode(String text, String field) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == 0) throw invalid(field);
            if (Character.isHighSurrogate(c)) {
                if (++i >= text.length() || !Character.isLowSurrogate(text.charAt(i)))
                    throw invalid(field);
            } else if (Character.isLowSurrogate(c)) throw invalid(field);
        }
    }

    private static void ownerId(String text, String field) {
        exact(text, field);
        if (text.codePointCount(0, text.length()) > 80) throw invalid(field);
    }

    private static void exact(String text, String field) {
        if (text == null
                || text.isBlank()
                || !text.equals(text.strip())
                || !text.equals(text.trim())) throw invalid(field);
        strictUnicode(text, field);
    }

    private static void boundedExact(String text, String field, int maxBytes) {
        exact(text, field);
        if (text.getBytes(StandardCharsets.UTF_8).length > maxBytes) throw invalid(field);
    }

    private static IllegalArgumentException invalid(String field) {
        return new IllegalArgumentException("Invalid operation-binding " + field);
    }
}
