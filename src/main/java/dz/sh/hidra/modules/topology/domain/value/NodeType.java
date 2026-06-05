/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NodeType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Topology node type enum.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Topology node type enum.
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
public enum NodeType implements ValueObject {

    /** Facility inlet node. */
    FACILITY_INLET,

    /** Facility outlet node. */
    FACILITY_OUTLET,

    /** Internal facility node. */
    FACILITY_INTERNAL,

    /** Pipeline junction node. */
    PIPELINE_JUNCTION,

    /** Pipeline valve point node. */
    PIPELINE_VALVE_POINT,

    /** Injection point node. */
    INJECTION_POINT,

    /** Extraction point node. */
    EXTRACTION_POINT,

    /** Purge point node. */
    PURGE_POINT,

    /** Vent point node. */
    VENT_POINT,

    /** Drain point node. */
    DRAIN_POINT,

    /** Metering point node. */
    METERING_POINT,

    /** Sampling point node. */
    SAMPLING_POINT,

    /** Scraper point node. */
    SCRAPER_POINT,

    /** Receipt point node. */
    RECEIPT_POINT,

    /** Delivery point node. */
    DELIVERY_POINT,

    /** Generic connection point node. */
    CONNECTION_POINT,

    /** Other topology node type. */
    OTHER;

}
