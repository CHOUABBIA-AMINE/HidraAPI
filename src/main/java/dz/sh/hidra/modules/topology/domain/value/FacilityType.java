/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Physical facility type enum for topology assets.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Physical facility type enum for topology assets.
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
public enum FacilityType implements ValueObject {

    /** Gas compression station. */
    COMPRESSION_STATION,

    /** Liquid pumping station. */
    PUMPING_STATION,

    /** Metering facility or station. */
    METERING_STATION,

    /** Valve station. */
    VALVE_STATION,

    /** Terminal facility such as export, delivery, marine, or storage terminal. */
    TERMINAL,

    /** Processing plant connected to the hydrocarbon network. */
    PROCESSING_PLANT,

    /** Production field interface to the pipeline network. */
    PRODUCTION_FIELD,

    /** Gathering center or gathering facility. */
    GATHERING_CENTER,

    /** Storage facility connected to the network. */
    STORAGE_FACILITY,

    /** Delivery facility connected to the network. */
    DELIVERY_FACILITY,

    /** Receipt facility connected to the network. */
    RECEIPT_FACILITY,

    /** Dispatching or network supervision center. */
    DISPATCHING_CENTER,

    /** Other physical facility type. */
    OTHER;

}
