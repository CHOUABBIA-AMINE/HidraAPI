/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Pipeline point asset or appurtenance type enum.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Pipeline point asset or appurtenance type enum.
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
public enum PipelineAppurtenanceType implements ValueObject {

    /** Valve installed along a pipeline. */
    VALVE,

    /** Injection point installed along a pipeline. */
    INJECTION_POINT,

    /** Extraction point installed along a pipeline. */
    EXTRACTION_POINT,

    /** Purge point installed along a pipeline. */
    PURGE_POINT,

    /** Vent point installed along a pipeline. */
    VENT_POINT,

    /** Drain point installed along a pipeline. */
    DRAIN_POINT,

    /** Sampling point installed along a pipeline. */
    SAMPLING_POINT,

    /** Metering point installed along a pipeline. */
    METERING_POINT,

    /** Scraper launcher installed along a pipeline. */
    SCRAPER_LAUNCHER,

    /** Scraper receiver installed along a pipeline. */
    SCRAPER_RECEIVER,

    /** Hot tap point installed along a pipeline. */
    HOT_TAP_POINT,

    /** Bypass point installed along a pipeline. */
    BYPASS_POINT,

    /** Generic connection point installed along a pipeline. */
    CONNECTION_POINT,

    /** Other pipeline appurtenance type. */
    OTHER;


    /**
     * Indicates whether this appurtenance type represents a valve.
     *
     * @return true when this type is VALVE
     */
    public boolean isValve() {
        return this == VALVE;
    }

}
