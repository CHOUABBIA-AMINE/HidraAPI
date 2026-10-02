/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Position
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Multilingual operational position or function.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.PositionLevel;
import dz.sh.hidra.modules.organization.domain.value.OrganizationCode;
import dz.sh.hidra.modules.organization.domain.value.PositionStatus;
import java.time.Instant;

/**
 * Defines an operational position with Arabic, French, and English titles and descriptions.
 *
 * <p>Business role: identifies the function held by employees within organization assignments.
 *
 * <p>Architecture role: domain model whose user-facing localized content is embedded directly on the owning entity.
 *
 * <p>Validation: localized text is normalized by trimming blanks to {@code null}; assignment and lifecycle rules
 * remain enforced by organization policies and application services.
 *
 * <p>Usage: callers must use the explicit language fields instead of a language-ambiguous description.
 *
 * @param id position identifier
 * @param code language-neutral business code
 * @param titleAr Arabic title
 * @param titleFr French title
 * @param titleEn English title
 * @param level position level
 * @param descriptionAr Arabic description
 * @param descriptionFr French description
 * @param descriptionEn English description
 * @param status position lifecycle status
 * @param createdAt creation timestamp
 * @param updatedAt last update timestamp
 */
public record Position(
        String id,
        String code,
        String titleAr,
        String titleFr,
        String titleEn,
        PositionLevel level,
        String descriptionAr,
        String descriptionFr,
        String descriptionEn,
        PositionStatus status,
        Instant createdAt,
        Instant updatedAt
) {

    public Position {
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidOrganizationValueException("Position code must not be blank.");
        }

        id = requireText(id, "Position ID is required.");
        code = OrganizationCode.of(code).value();
        if (level == null) {
            throw new InvalidOrganizationValueException("Position level is required.");
        }
        if (status == null) {
            throw new InvalidOrganizationValueException("Position status is required.");
        }
        titleAr = normalize(titleAr);
        titleFr = normalize(titleFr);
        titleEn = normalize(titleEn);
        descriptionAr = normalize(descriptionAr);
        descriptionFr = normalize(descriptionFr);
        descriptionEn = normalize(descriptionEn);
    }
    private static String requireText(String value, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new InvalidOrganizationValueException(message);
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
