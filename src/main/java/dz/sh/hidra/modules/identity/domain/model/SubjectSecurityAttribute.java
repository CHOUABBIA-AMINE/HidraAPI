/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SubjectSecurityAttribute
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("SubjectSecurityAttribute id must not be blank.");
        }
        // HRA-051 required: subjectType
        if (subjectType == null) {
            throw new InvalidIdentityValueException("SubjectSecurityAttribute subject type must not be null.");
        }
        // HRA-051 required: subjectId
        if (subjectId == null || subjectId.isBlank()) {
            throw new InvalidIdentityValueException("SubjectSecurityAttribute subject id must not be blank.");
        }
        // HRA-051 required: attributeDefinitionId
        if (attributeDefinitionId == null || attributeDefinitionId.isBlank()) {
            throw new InvalidIdentityValueException("SubjectSecurityAttribute attribute definition id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("SubjectSecurityAttribute status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidIdentityValueException("SubjectSecurityAttribute valid to must not be before valid from.");
        }

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
