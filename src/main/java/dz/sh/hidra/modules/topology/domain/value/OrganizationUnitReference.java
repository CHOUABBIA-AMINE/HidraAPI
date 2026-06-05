/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Neutral organization unit reference value object.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents a neutral reference to an organization unit.
 *
 * <p>Business role:
 * This value object lets topology identify the organization unit that owns or operates a physical
 * topology asset without importing organization implementation classes.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object. It must not depend on organization domain models,
 * organization repositories, identity, Spring, JPA, REST, measurement, flow, risk, workflow, or
 * infrastructure code.
 *
 * <p>Validation:
 * Reference type, identifier, code, and name are all required and trimmed.
 *
 * <p>Usage:
 * Use this type for neutral cross-module references only.
 *
 * @param referenceType reference type, usually <code>ORGANIZATION_UNIT</code>
 * @param referenceId external organization unit identifier
 * @param referenceCode external organization unit code
 * @param referenceName external organization unit name
 */
public record OrganizationUnitReference(
        String referenceType,
        String referenceId,
        String referenceCode,
        String referenceName) implements ValueObject {

    public OrganizationUnitReference {
        referenceType = requireText(referenceType, "referenceType");
        referenceId = requireText(referenceId, "referenceId");
        referenceCode = requireText(referenceCode, "referenceCode");
        referenceName = requireText(referenceName, "referenceName");
    }

    /**
     * Creates an organization unit reference.
     *
     * @param referenceType reference type
     * @param referenceId reference identifier
     * @param referenceCode reference code
     * @param referenceName reference name
     * @return validated reference
     */
    public static OrganizationUnitReference of(
            String referenceType,
            String referenceId,
            String referenceCode,
            String referenceName) {

        return new OrganizationUnitReference(referenceType, referenceId, referenceCode, referenceName);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("OrganizationUnitReference " + fieldName + " must not be null or blank.");
        }
        return value.trim();
    }
}
