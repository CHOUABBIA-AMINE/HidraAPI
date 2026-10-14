/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationVerificationSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Captured verification-time generation and exact provider provenance; never a trusted receipt.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

import java.time.Instant;

/**
 * Verification-time source snapshot, not a standalone authentication attestation. Only the private
 * trusted registry may associate it with a consumed producer receipt.
 */
public record AuthenticationVerificationSnapshot(
        String userId,
        String authenticationType,
        String providerId,
        long providerSecurityVersion,
        String externalIdentityId,
        String externalSubject,
        Long externalSecurityVersion,
        String sourceIssuer,
        Instant sourceExpiresAt,
        String credentialId,
        Long credentialSecurityVersion) {
    public AuthenticationVerificationSnapshot {
        userId = require(userId);
        authenticationType = require(authenticationType);
        providerId = require(providerId);
        if (providerSecurityVersion <= 0)
            throw new IllegalArgumentException("Invalid provider generation");
        switch (authenticationType) {
            case "LOCAL" -> {
                if (externalIdentityId != null
                        || externalSubject != null
                        || externalSecurityVersion != null
                        || sourceIssuer != null
                        || sourceExpiresAt != null
                        || credentialSecurityVersion == null
                        || credentialSecurityVersion <= 0) throw invalid();
                credentialId = require(credentialId);
            }
            case "LDAP", "ACTIVE_DIRECTORY" -> {
                if (credentialId != null
                        || credentialSecurityVersion != null
                        || sourceIssuer != null
                        || sourceExpiresAt != null) throw invalid();
                externalIdentityId = require(externalIdentityId);
                externalSubject = source(externalSubject);
                if (externalSecurityVersion == null || externalSecurityVersion <= 0)
                    throw invalid();
            }
            case "OIDC" -> {
                if (credentialId != null
                        || credentialSecurityVersion != null
                        || sourceExpiresAt == null) throw invalid();
                externalIdentityId = require(externalIdentityId);
                externalSubject = source(externalSubject);
                sourceIssuer = source(sourceIssuer);
                if (sourceExpiresAt.getEpochSecond() < -210866803200L
                        || sourceExpiresAt.getEpochSecond() >= 9224318016000L
                        || sourceExpiresAt.getNano() % 1000 != 0) throw invalid();
                if (externalSecurityVersion == null || externalSecurityVersion <= 0)
                    throw invalid();
            }
            default -> throw invalid();
        }
    }

    @Override
    public String toString() {
        return "AuthenticationVerificationSnapshot[authenticationType="
                + authenticationType
                + ", provenance=<redacted>]";
    }

    private static String source(String text) {
        if (text == null || text.isBlank()) throw invalid();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == 0) throw invalid();
            if (Character.isHighSurrogate(c)) {
                if (++i >= text.length() || !Character.isLowSurrogate(text.charAt(i)))
                    throw invalid();
            } else if (Character.isLowSurrogate(c)) throw invalid();
        }
        return text;
    }

    private static String require(String text) {
        if (text == null
                || text.isBlank()
                || !text.equals(text.strip())
                || !text.equals(text.trim())
                || text.codePointCount(0, text.length()) > 80) throw invalid();
        source(text);
        return text;
    }

    private static IllegalArgumentException invalid() {
        return new IllegalArgumentException("Invalid verification provenance");
    }
}
