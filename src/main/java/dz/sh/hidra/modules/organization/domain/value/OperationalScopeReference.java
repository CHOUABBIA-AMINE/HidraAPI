/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Canonical typed reference to an operational-scope owner target before registry identity.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;

/**
 * Canonical owner reference used before an operational scope receives its registry ID.
 *
 * <p>Business role: this value identifies the authoritative owner target by type and
 * by that owner's native identifier. It deliberately excludes mutable code/name
 * display data and does not pretend that the owner target ID is the generated
 * operational-scope registry ID.</p>
 *
 * <p>Architecture role: this is a domain value object with no dependency on topology
 * or any other owner module. Owner existence and assignability are validated later
 * through application ports.</p>
 *
 * <p>Validation: type is mandatory; GLOBAL has no target ID; governed entity-backed
 * types require a non-blank target ID; CUSTOM is rejected until a separately approved
 * owner namespace exists.</p>
 *
 * <p>Usage: use this value only for typed owner identity. Persisted responsibility
 * assignments must reference the generated {@code OperationalScope.id} instead.</p>
 *
 * @param type governed operational-scope target category
 * @param targetId owner-native target identifier; null only for GLOBAL
 */
public record OperationalScopeReference(
        OperationalScopeType type,
        String targetId
) {

    public OperationalScopeReference {
        if (type == null) {
            throw new InvalidOrganizationValueException("Operational scope type is required.");
        }

        targetId = normalize(targetId);

        if (!type.isGoverned()) {
            throw new InvalidOrganizationValueException(
                    "CUSTOM scope requires a separately approved owner contract."
            );
        }

        if (type.isGlobal()) {
            if (targetId != null) {
                throw new InvalidOrganizationValueException(
                        "GLOBAL scope must not reference a target object."
                );
            }
        } else if (type.requiresTargetId() && targetId == null) {
            throw new InvalidOrganizationValueException(
                    "Entity-backed scope must reference a target object."
            );
        }
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
