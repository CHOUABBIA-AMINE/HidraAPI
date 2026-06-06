/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Trilingual role display name value object.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Human-readable trilingual display name for an identity role.
 *
 * <p>Business role:
 * Names a role in Arabic, French, and English so administrators can display role catalogs without
 * hardcoded UI labels.
 *
 * <p>Architecture role:
 * Immutable domain value object used by the role aggregate and application DTOs. Compatibility
 * helpers keep existing single-label callers compiling while new code should use all three fields.
 *
 * <p>Validation responsibility:
 * Each language value is trimmed and must be 3 to 100 characters long.
 *
 * @param nameAr Arabic display name
 * @param nameFr French display name
 * @param nameEn English display name
 */
public record RoleName(String nameAr, String nameFr, String nameEn) implements ValueObject {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 100;

    public RoleName {
        nameAr = normalize(nameAr, "RoleName Arabic value");
        nameFr = normalize(nameFr, "RoleName French value");
        nameEn = normalize(nameEn, "RoleName English value");
    }

    public RoleName(String value) {
        this(value, value, value);
    }

    public static RoleName of(String value) {
        return new RoleName(value);
    }

    public static RoleName of(String nameAr, String nameFr, String nameEn) {
        return new RoleName(nameAr, nameFr, nameEn);
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
            throw new InvalidValueObjectException(label + " must not be blank.");
        }
        String normalized = value.trim();
        if (normalized.length() < MIN_LENGTH || normalized.length() > MAX_LENGTH) {
            throw new InvalidValueObjectException(label + " must contain between 3 and 100 characters.");
        }
        return normalized;
    }
}
