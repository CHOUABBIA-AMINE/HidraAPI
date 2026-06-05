/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Topology asset lifecycle status enum.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Topology asset lifecycle status enum.
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
public enum TopologyStatus implements ValueObject {

    /** Asset is known but not operational yet. */
    PLANNED,

    /** Asset is operational and usable. */
    ACTIVE,

    /** Asset is temporarily not operational. */
    INACTIVE,

    /** Asset is unavailable because of maintenance. */
    UNDER_MAINTENANCE,

    /** Asset is no longer operational but remains historically relevant. */
    RETIRED,

    /** Asset has been physically or administratively removed from service. */
    DECOMMISSIONED;


    /**
     * Indicates whether this status allows ordinary operational use.
     *
     * @return true when the status is ACTIVE
     */
    public boolean allowsOperationalUse() {
        return this == ACTIVE;
    }

    /**
     * Indicates whether the asset is no longer expected to return to service.
     *
     * @return true for RETIRED or DECOMMISSIONED
     */
    public boolean isTerminalLifecycleState() {
        return this == RETIRED || this == DECOMMISSIONED;
    }

}
