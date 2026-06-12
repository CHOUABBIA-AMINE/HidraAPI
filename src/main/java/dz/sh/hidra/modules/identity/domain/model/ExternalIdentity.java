/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalIdentity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Links a Hidra User to an external IDM subject.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Links a Hidra User to an external IDM subject.
 *
     * @param id id
 * @param userId userId
 * @param identityProviderId identityProviderId
 * @param externalSubject externalSubject
 * @param externalImmutableId externalImmutableId
 * @param externalUsername externalUsername
 * @param externalEmail externalEmail
 * @param externalDisplayName externalDisplayName
 * @param externalDistinguishedName externalDistinguishedName
 * @param externalAttributesSnapshot externalAttributesSnapshot
 * @param lastLoginAt lastLoginAt
 * @param lastSyncedAt lastSyncedAt
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record ExternalIdentity(
        String id,
    String userId,
    String identityProviderId,
    String externalSubject,
    String externalImmutableId,
    String externalUsername,
    String externalEmail,
    String externalDisplayName,
    String externalDistinguishedName,
    String externalAttributesSnapshot,
    Instant lastLoginAt,
    Instant lastSyncedAt,
    ExternalIdentityStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    public ExternalIdentity {
    id = normalize(id);
    userId = normalize(userId);
    identityProviderId = normalize(identityProviderId);
    externalSubject = normalize(externalSubject);
    externalImmutableId = normalize(externalImmutableId);
    externalUsername = normalize(externalUsername);
    externalEmail = normalize(externalEmail);
    externalDisplayName = normalize(externalDisplayName);
    externalDistinguishedName = normalize(externalDistinguishedName);
    externalAttributesSnapshot = normalize(externalAttributesSnapshot);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
