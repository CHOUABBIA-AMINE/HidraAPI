/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Operational scope type enum.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Represents the type of future operational or topology scope referenced by organization.
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
public enum OperationalScopeType implements ValueObject {

    /** Generic future topology station reference. */
    TOPOLOGY_STATION,

    /** Future topology compression station reference. */
    TOPOLOGY_COMPRESSION_STATION,

    /** Future topology pumping station reference. */
    TOPOLOGY_PUMPING_STATION,

    /** Future topology delivery station reference. */
    TOPOLOGY_DELIVERY_STATION,

    /** Future topology metering station reference. */
    TOPOLOGY_METERING_STATION,

    /** Future topology pipeline reference. */
    TOPOLOGY_PIPELINE,

    /** Future topology or operational region reference. */
    TOPOLOGY_REGION,

    /** Future topology facility reference. */
    TOPOLOGY_FACILITY,

    /** Generic operational scope when no specialized topology type exists yet. */
    GENERIC_OPERATIONAL_SCOPE;


    /**
     * Indicates whether this scope type refers to any station-like topology asset.
     *
     * @return true when the scope type is station-oriented
     */
    public boolean isStationScope() {
        return this == TOPOLOGY_STATION
                || this == TOPOLOGY_COMPRESSION_STATION
                || this == TOPOLOGY_PUMPING_STATION
                || this == TOPOLOGY_DELIVERY_STATION
                || this == TOPOLOGY_METERING_STATION;
    }

}
