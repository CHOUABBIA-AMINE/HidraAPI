/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyName
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
 * Represents a human-readable trilingual name for a topology asset.
 *
 * <p>Business role:
 * Captures the Arabic, French, and English display names of a physical topology asset.
 *
 * <p>Architecture role:
 * Pure topology domain value object. Compatibility helpers keep existing single-label callers
 * compiling while new code should use all three fields.
 *
 * <p>Validation:
 * Each language value is trimmed and must be 2 to 160 characters long.
 *
 * @param nameAr Arabic display name
 * @param nameFr French display name
 * @param nameEn English display name
 */
public record TopologyName(String nameAr, String nameFr, String nameEn) implements ValueObject {

    public TopologyName {
        nameAr = normalize(nameAr, "TopologyName Arabic value");
        nameFr = normalize(nameFr, "TopologyName French value");
        nameEn = normalize(nameEn, "TopologyName English value");
    }

    public TopologyName(String value) {
        this(value, value, value);
    }

    public static TopologyName of(String value) {
        return new TopologyName(value);
    }

    public static TopologyName of(String nameAr, String nameFr, String nameEn) {
        return new TopologyName(nameAr, nameFr, nameEn);
    }

    /**
     * Compatibility projection used by existing single-label code paths.
     *
     * @return English display name
     */
    public String value() {
        return nameEn;
    }

    private static String normalize(String value, String label) {
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
