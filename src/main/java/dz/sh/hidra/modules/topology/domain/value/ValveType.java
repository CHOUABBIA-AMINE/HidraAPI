/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ValveType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Pipeline valve type enum.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Pipeline valve type enum.
 *
 * <p>Business role:
 * This enum restricts topology domain values to an explicit controlled set.
 *
 * <p>Architecture role:
 * This is a pure topology domain value object enum and must not depend on API, persistence,
 * identity, organization, platform, Spring, JPA, measurement, flow, risk, or workflow code.
 *
 * <p>Validation:
 * The enum prevents unbounded string values for this topology concept.
 *
 * <p>Usage:
 * Use this enum in topology domain and application code when this constrained value is required.
 */
public enum ValveType implements ValueObject {

    /** Block valve. */
    BLOCK_VALVE,

    /** Sectionalizing valve. */
    SECTIONALIZING_VALVE,

    /** Isolation valve. */
    ISOLATION_VALVE,

    /** Shutdown valve. */
    SHUTDOWN_VALVE,

    /** Control valve. */
    CONTROL_VALVE,

    /** Check valve. */
    CHECK_VALVE,

    /** Relief valve. */
    RELIEF_VALVE,

    /** Pressure regulating valve. */
    PRESSURE_REGULATING_VALVE,

    /** Bypass valve. */
    BYPASS_VALVE,

    /** Drain valve. */
    DRAIN_VALVE,

    /** Vent valve. */
    VENT_VALVE,

    /** Emergency shutdown valve. */
    ESD_VALVE,

    /** Manual valve. */
    MANUAL_VALVE,

    /** Motorized valve. */
    MOTORIZED_VALVE,

    /** Other valve type. */
    OTHER;

}
