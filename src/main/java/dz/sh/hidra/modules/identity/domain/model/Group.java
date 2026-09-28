/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Group
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Represents an identity-owned security group.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Represents an identity-owned security group.
 *
     * @param id id
 * @param code code
 * @param nameAr nameAr
 * @param nameFr nameFr
 * @param nameEn nameEn
 * @param description description
 * @param groupType groupType
 * @param sourceProviderId sourceProviderId
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record Group(
        String id,
    String code,
    String nameAr,
    String nameFr,
    String nameEn,
    String description,
    GroupType groupType,
    String sourceProviderId,
    GroupStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    public Group {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("Group id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidIdentityValueException("Group code must not be blank.");
        }
        // HRA-051 required: groupType
        if (groupType == null) {
            throw new InvalidIdentityValueException("Group group type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("Group status must not be null.");
        }

    id = normalize(id);
    code = normalize(code);
    nameAr = normalize(nameAr);
    nameFr = normalize(nameFr);
    nameEn = normalize(nameEn);
    description = normalize(description);
    sourceProviderId = normalize(sourceProviderId);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
