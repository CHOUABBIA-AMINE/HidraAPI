/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SubjectSecurityAttribute
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Stores a security attribute assigned to a subject.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Stores a security attribute assigned to a subject.
 *
     * @param id id
 * @param subjectType subjectType
 * @param subjectId subjectId
 * @param attributeDefinitionId attributeDefinitionId
 * @param attributeValue attributeValue
 * @param sourceProviderId sourceProviderId
 * @param validFrom validFrom
 * @param validTo validTo
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record SubjectSecurityAttribute(
        String id,
    SubjectAttributeOwnerType subjectType,
    String subjectId,
    String attributeDefinitionId,
    String attributeValue,
    String sourceProviderId,
    Instant validFrom,
    Instant validTo,
    PermissionStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    public SubjectSecurityAttribute {
    id = normalize(id);
    subjectId = normalize(subjectId);
    attributeDefinitionId = normalize(attributeDefinitionId);
    attributeValue = normalize(attributeValue);
    sourceProviderId = normalize(sourceProviderId);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
