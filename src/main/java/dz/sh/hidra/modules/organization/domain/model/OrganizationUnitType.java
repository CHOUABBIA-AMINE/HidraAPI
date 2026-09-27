/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Language-embedded catalog entry for organization unit types.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitKind;
import dz.sh.hidra.modules.organization.domain.value.OrganizationCode;
import java.time.Instant;

/**
 * Defines one organization-unit type with Arabic, French, and English content on the owning entity.
 *
 * <p>Business role: classifies organization units such as divisions, regions, stations, teams, and departments.
 *
 * <p>Architecture role: domain model persisted directly by the organization module; localized labels are not
 * represented by a separate translation aggregate in the target model.
 *
 * <p>Validation: textual values are normalized by trimming blanks to {@code null}; lifecycle and catalog policy
 * validation remains owned by organization application/domain services.
 *
 * <p>Usage: use {@code nameAr/nameFr/nameEn} and {@code descriptionAr/descriptionFr/descriptionEn} as the
 * canonical localized fields. The legacy translation model remains transitional until ORG-038.
 *
 * @param id catalog identifier
 * @param code language-neutral business code
 * @param kind organization-unit kind
 * @param nameAr Arabic display name
 * @param nameFr French display name
 * @param nameEn English display name
 * @param descriptionAr Arabic description
 * @param descriptionFr French description
 * @param descriptionEn English description
 * @param active whether this catalog entry is active
 * @param createdAt creation timestamp
 * @param updatedAt last update timestamp
 */
public record OrganizationUnitType(
        String id,
        String code,
        OrganizationUnitKind kind,
        String nameAr,
        String nameFr,
        String nameEn,
        String descriptionAr,
        String descriptionFr,
        String descriptionEn,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public OrganizationUnitType {
        id = normalize(id);
        code = OrganizationCode.of(code).value();
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        descriptionAr = normalize(descriptionAr);
        descriptionFr = normalize(descriptionFr);
        descriptionEn = normalize(descriptionEn);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
