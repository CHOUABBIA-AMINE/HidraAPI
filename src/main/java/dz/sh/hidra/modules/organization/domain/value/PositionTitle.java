/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionTitle
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Trilingual position title value object.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the trilingual display title of an organization position.
 *
 * <p>Business role:
 * Captures Arabic, French, and English titles for a position inside the organization bounded context.
 *
 * <p>Architecture role:
 * Domain value object independent from Spring, JPA, REST DTO validation, identity, topology, and
 * platform infrastructure. Compatibility helpers keep existing single-label callers compiling while
 * new code should use all three fields.
 *
 * <p>Validation:
 * Each language value is trimmed and must be 2 to 120 characters long.
 *
 * @param titleAr Arabic display title
 * @param titleFr French display title
 * @param titleEn English display title
 */
public record PositionTitle(String titleAr, String titleFr, String titleEn) implements ValueObject {

    public PositionTitle {
        titleAr = normalize(titleAr, "PositionTitle Arabic value");
        titleFr = normalize(titleFr, "PositionTitle French value");
        titleEn = normalize(titleEn, "PositionTitle English value");
    }

    public PositionTitle(String value) {
        this(value, value, value);
    }

    public static PositionTitle of(String value) {
        return new PositionTitle(value);
    }

    public static PositionTitle of(String titleAr, String titleFr, String titleEn) {
        return new PositionTitle(titleAr, titleFr, titleEn);
    }

    /**
     * Compatibility projection used by existing single-label code paths.
     *
     * @return English display title
     */
    public String value() {
        return titleEn;
    }

    private static String normalize(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(label + " must not be null or blank.");
        }
        String normalized = value.trim();
        if (normalized.length() < 2 || normalized.length() > 120) {
            throw new InvalidValueObjectException(label + " length must be between 2 and 120 characters.");
        }
        return normalized;
    }
}
