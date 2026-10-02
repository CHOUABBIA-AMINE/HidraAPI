/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalCredential
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Represents the Identity-owned persisted credential metadata for LOCAL authentication.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import java.time.Instant;

/**
 * Persistent LOCAL credential contract containing only the one-way password hash and credential metadata.
 *
 * @param id credential identifier
 * @param userId stable Hidra user identifier
 * @param passwordHash one-way adaptive password hash
 * @param credentialStatus credential lifecycle status
 * @param passwordChangedAt last password change timestamp
 * @param createdAt creation timestamp
 * @param updatedAt last update timestamp
 */
public record LocalCredential(
        String id,
        String userId,
        String passwordHash,
        String credentialStatus,
        Instant passwordChangedAt,
        Instant createdAt,
        Instant updatedAt
) {

    public LocalCredential {
        id = requireText(id, "id");
        userId = requireText(userId, "userId");
        passwordHash = requireText(passwordHash, "passwordHash");
        credentialStatus = requireText(credentialStatus, "credentialStatus");
    }

    private static String requireText(String value, String fieldName) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return normalized;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
