/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalOwnerReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Neutral operational owner reference value object.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents a neutral reference to an operational owner.
 *
 * <p>Business role:
 * This value object identifies the operational owner of a topology asset without coupling topology
 * to organization implementation classes.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object. It must not depend on organization domain models,
 * organization repositories, identity, Spring, JPA, REST, measurement, flow, risk, workflow, or
 * infrastructure code.
 *
 * <p>Validation:
 * Owner type, identifier, code, and name are all required and trimmed.
 *
 * <p>Usage:
 * Use this type when a physical pipeline system, pipeline, facility, or segment needs a neutral
 * operational owner reference.
 *
 * @param ownerType owner type, for example <code>ORGANIZATION_UNIT</code>
 * @param ownerId owner identifier
 * @param ownerCode owner code
 * @param ownerName owner display name
 */
public record OperationalOwnerReference(
        String ownerType,
        String ownerId,
        String ownerCode,
        String ownerName) implements ValueObject {

    public OperationalOwnerReference {
        ownerType = requireText(ownerType, "ownerType");
        ownerId = requireText(ownerId, "ownerId");
        ownerCode = requireText(ownerCode, "ownerCode");
        ownerName = requireText(ownerName, "ownerName");
    }

    /**
     * Creates an operational owner reference.
     *
     * @param ownerType owner type
     * @param ownerId owner identifier
     * @param ownerCode owner code
     * @param ownerName owner name
     * @return validated owner reference
     */
    public static OperationalOwnerReference of(
            String ownerType,
            String ownerId,
            String ownerCode,
            String ownerName) {

        return new OperationalOwnerReference(ownerType, ownerId, ownerCode, ownerName);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("OperationalOwnerReference " + fieldName + " must not be null or blank.");
        }
        return value.trim();
    }
}
