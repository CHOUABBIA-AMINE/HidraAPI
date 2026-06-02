/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Matrix reporting line type enum.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the type of employee-to-manager reporting relationship.
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
public enum ReportingLineType implements ValueObject {

    /** Primary hierarchical reporting line. */
    LINE,

    /** Operational reporting line for day-to-day operational responsibility. */
    OPERATIONAL,

    /** Functional reporting line for specialist or cross-functional direction. */
    FUNCTIONAL,

    /** Administrative reporting line. */
    ADMINISTRATIVE,

    /** Technical reporting line for expertise or engineering authority. */
    TECHNICAL,

    /** Dotted-line reporting relationship. */
    DOTTED_LINE;


    /**
     * Indicates whether this reporting type may usually be repeated for the same employee.
     *
     * @return true when multiple active reporting lines of this type may be allowed by policy
     */
    public boolean allowsMultipleActiveLines() {
        return this == FUNCTIONAL || this == ADMINISTRATIVE || this == TECHNICAL || this == DOTTED_LINE;
    }

}
