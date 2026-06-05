/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Topology equipment reference type enum.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Topology equipment reference type enum.
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
public enum EquipmentType implements ValueObject {

    /** Compressor unit or compressor package. */
    COMPRESSOR,

    /** Pump unit or pump package. */
    PUMP,

    /** Valve component. */
    VALVE,

    /** Metering equipment. */
    METER,

    /** Separator equipment. */
    SEPARATOR,

    /** Scraper launcher equipment. */
    SCRAPER_LAUNCHER,

    /** Scraper receiver equipment. */
    SCRAPER_RECEIVER,

    /** Actuator component. */
    ACTUATOR,

    /** Local control panel or cabinet. */
    CONTROL_PANEL,

    /** Instrumentation component. */
    INSTRUMENTATION,

    /** Other equipment type. */
    OTHER;

}
