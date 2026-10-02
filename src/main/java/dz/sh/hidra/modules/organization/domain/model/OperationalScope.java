/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScope
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-24
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Canonical registry identity for one validated operational-scope target.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

/**
 * The registered identity of an operational scope, distinct from its target asset or unit.
 *
 * <p>Business role: a single registry ID may be referenced by multiple, independently
 * effective-dated responsibility assignments. Current target names and codes belong
 * to the target's owning module and are deliberately absent from this record.
 *
 * <p>Architecture role: an organization-domain model that refers to foreign domain
 * objects solely through a neutral typed identifier; it does not import topology.
 *
 * <p>Validation: this record checks its local shape. A registration use case must
 * additionally establish target existence, ownership, assignability and uniqueness
 * before the registry ID is committed to the database.
 *
 * <p>Usage: use only for a scope that has already received a database-generated ID.
 * The persistence/application layers own registration and target resolution.
 *
 * @param id positive, database-generated registry identifier; not a target object ID
 * @param type scope target category; required
 * @param targetId stable identifier in the owning module's native format; null for GLOBAL
 */
public record OperationalScope(
        Long id,
        OperationalScopeType type,
        String targetId
) {

    public OperationalScope {
        if (id == null || id <= 0) {
            throw new InvalidOrganizationValueException("Registered operational scope ID must be positive.");
        }
        if (type == null) {
            throw new InvalidOrganizationValueException("Operational scope type is required.");
        }
        targetId = normalize(targetId);
        if (type == OperationalScopeType.GLOBAL) {
            if (targetId != null) {
                throw new InvalidOrganizationValueException("GLOBAL scope must not reference a target object.");
            }
        } else if (type == OperationalScopeType.CUSTOM) {
            // The existing enum does not define a governed target namespace or resolver.
            throw new InvalidOrganizationValueException("CUSTOM scope requires a separately approved owner contract.");
        } else if (targetId == null) {
            throw new InvalidOrganizationValueException("Entity-backed scope must reference a target object.");
        }
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
