/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Organization unit lifecycle status enum.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the lifecycle status of an organization unit.
 *
 * <p>Business role:
 * This enum defines the allowed business states or categories used by organization domain objects.
 *
 * <p>Architecture role:
 * This is a domain value object enum. It must not depend on API, persistence, identity, topology,
 * platform, Spring, or JPA code.
 *
 * <p>Validation:
 * The enum restricts values to the listed constants and prevents unbounded string status/type values.
 *
 * <p>Usage:
 * Use this enum in organization domain and application code when a constrained value is required.
 */
public enum OrganizationUnitStatus implements ValueObject {

    /** Organization unit is planned but not operational yet. */
    PLANNED,

    /** Organization unit is active and may receive employee assignments. */
    ACTIVE,

    /** Organization unit is disabled and cannot receive new employee assignments. */
    DISABLED,

    /** Organization unit is historical and retained for traceability. */
    ARCHIVED;


    /**
     * Indicates whether this status allows new employee assignments.
     *
     * @return true when the unit is active
     */
    public boolean allowsEmployeeAssignment() {
        return this == ACTIVE;
    }

}
