/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Organization unit type enum.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the type of organization unit.
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
public enum OrganizationUnitType implements ValueObject {

    /** Company-level organization unit. */
    COMPANY,

    /** Division-level organization unit. */
    DIVISION,

    /** Direction-level organization unit. */
    DIRECTION,

    /** Department-level organization unit. */
    DEPARTMENT,

    /** Operational region organization unit. */
    REGION,

    /** Operational area organization unit. */
    AREA,

    /** Operational district organization unit. */
    DISTRICT,

    /** Station-as-organization-unit for people and responsibility structure; physical station asset belongs to topology. */
    STATION,

    /** Team-level organization unit. */
    TEAM,

    /** Temporary project team organization unit. */
    PROJECT_TEAM,

    /** Other organization unit type not covered by the standard list. */
    OTHER;


    /**
     * Indicates whether this type represents a station organization unit.
     *
     * @return true when the organization unit represents station people and responsibility
     */
    public boolean isStationOrganizationUnit() {
        return this == STATION;
    }

}
