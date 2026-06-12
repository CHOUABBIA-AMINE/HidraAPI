/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Role
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Groups permissions into a reusable authorization package.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Groups permissions into a reusable authorization package.
 *
     * @param id id
 * @param code code
 * @param nameAr nameAr
 * @param nameFr nameFr
 * @param nameEn nameEn
 * @param description description
 * @param roleType roleType
 * @param status status
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record Role(
        String id,
    String code,
    String nameAr,
    String nameFr,
    String nameEn,
    String description,
    RoleType roleType,
    RoleStatus status,
    Instant createdAt,
    Instant updatedAt
) {

    public Role {
    id = normalize(id);
    code = normalize(code);
    nameAr = normalize(nameAr);
    nameFr = normalize(nameFr);
    nameEn = normalize(nameEn);
    description = normalize(description);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
