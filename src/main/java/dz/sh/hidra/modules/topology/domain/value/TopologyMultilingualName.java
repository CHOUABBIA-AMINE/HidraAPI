/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyMultilingualName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Trilingual topology display name value object.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents a human-readable topology business name in Arabic, French, and English.
 *
 * <p>Business role:
 * Stores first-class multilingual display labels for user-facing topology assets.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object and must not depend on API, persistence, Spring, JPA,
 * identity, organization, platform, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * All three names are required for API-created pipeline labels during Correction 02.
 *
 * @param nameAr Arabic display name
 * @param nameFr French display name
 * @param nameEn English display name
 */
public record TopologyMultilingualName(
        String nameAr,
        String nameFr,
        String nameEn) implements ValueObject {

    public TopologyMultilingualName {
        nameAr = normalizeRequired(nameAr, "Arabic topology name");
        nameFr = normalizeRequired(nameFr, "French topology name");
        nameEn = normalizeRequired(nameEn, "English topology name");
    }

    public static TopologyMultilingualName of(String nameAr, String nameFr, String nameEn) {
        return new TopologyMultilingualName(nameAr, nameFr, nameEn);
    }

    private static String normalizeRequired(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(label + " must not be null or blank.");
        }
        String normalized = value.trim();
        if (normalized.length() < 2 || normalized.length() > 160) {
            throw new InvalidValueObjectException(label + " length must be between 2 and 160 characters.");
        }
        return normalized;
    }
}
