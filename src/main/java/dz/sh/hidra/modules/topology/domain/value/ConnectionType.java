/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConnectionType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Topology connection type enum.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Topology connection type enum.
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
public enum ConnectionType implements ValueObject {

    /** Connection represented by a pipeline segment. */
    PIPELINE_SEGMENT,

    /** Internal physical connection inside a facility. */
    FACILITY_INTERNAL,

    /** Connection through or around a valve. */
    VALVE_CONNECTION,

    /** Metering connection. */
    METERING_CONNECTION,

    /** Junction connection. */
    JUNCTION_CONNECTION,

    /** Connection involving a pipeline appurtenance. */
    APPURTENANCE_CONNECTION,

    /** Other topology connection type. */
    OTHER;

}
