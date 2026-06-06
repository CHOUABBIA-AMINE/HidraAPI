/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Trilingual organization unit display name value object.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the trilingual display name of an organization unit.
 *
 * <p>Business role:
 * Captures Arabic, French, and English names of an organization unit inside the organization bounded
 * context.
 *
 * <p>Architecture role:
 * Domain value object independent from Spring, JPA, REST DTO validation, identity, topology, and
 * platform infrastructure. Compatibility helpers keep existing single-label callers compiling while
 * new code should use all three fields.
 *
 * <p>Validation:
 * Each language value is trimmed and must be 2 to 160 characters long.
 *
 * @param nameAr Arabic display name
 * @param nameFr French display name
 * @param nameEn English display name
 */
public record OrganizationUnitName(String nameAr, String nameFr, String nameEn) implements ValueObject {

    public OrganizationUnitName {
        nameAr = normalize(nameAr, "OrganizationUnitName Arabic value");
        nameFr = normalize(nameFr, "OrganizationUnitName French value");
        nameEn = normalize(nameEn, "OrganizationUnitName English value");
    }

    public OrganizationUnitName(String value) {
        this(value, value, value);
    }

    public static OrganizationUnitName of(String value) {
        return new OrganizationUnitName(value);
    }

    public static OrganizationUnitName of(String nameAr, String nameFr, String nameEn) {
        return new OrganizationUnitName(nameAr, nameFr, nameEn);
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
