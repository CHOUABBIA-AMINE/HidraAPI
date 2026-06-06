/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyMultilingualDescription
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Trilingual topology description value object.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents optional human-readable topology descriptions in Arabic, French, and English.
 *
 * <p>Business role:
 * Stores first-class multilingual descriptive text for user-facing topology assets.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object and must not depend on API, persistence, Spring, JPA,
 * identity, organization, platform, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Blank descriptions are normalized to null and non-blank descriptions are capped at 500 characters.
 *
 * @param descriptionAr optional Arabic description
 * @param descriptionFr optional French description
 * @param descriptionEn optional English description
 */
public record TopologyMultilingualDescription(
        String descriptionAr,
        String descriptionFr,
        String descriptionEn) implements ValueObject {

    public TopologyMultilingualDescription {
        descriptionAr = normalizeOptional(descriptionAr, "Arabic topology description");
        descriptionFr = normalizeOptional(descriptionFr, "French topology description");
        descriptionEn = normalizeOptional(descriptionEn, "English topology description");
    }

    public static TopologyMultilingualDescription of(String descriptionAr, String descriptionFr, String descriptionEn) {
        if ((descriptionAr == null || descriptionAr.isBlank())
                && (descriptionFr == null || descriptionFr.isBlank())
                && (descriptionEn == null || descriptionEn.isBlank())) {
            return null;
        }
        return new TopologyMultilingualDescription(descriptionAr, descriptionFr, descriptionEn);
    }

    private static String normalizeOptional(String value, String label) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String normalized = value.trim();
        if (normalized.length() > 500) {
            throw new InvalidValueObjectException(label + " must not exceed 500 characters.");
        }
        return normalized;
    }
}
