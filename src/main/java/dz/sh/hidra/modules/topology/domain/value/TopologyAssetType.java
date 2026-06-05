/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyAssetType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Topology asset type enum for neutral parent references.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Topology asset type enum for neutral parent references.
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
public enum TopologyAssetType implements ValueObject {

    /** Pipeline system asset. */
    PIPELINE_SYSTEM,

    /** Pipeline asset. */
    PIPELINE,

    /** Physical facility asset. */
    FACILITY,

    /** Topology node asset. */
    NODE,

    /** Pipeline segment asset. */
    SEGMENT,

    /** Pipeline appurtenance asset. */
    APPURTENANCE,

    /** Topology connection asset. */
    CONNECTION,

    /** Equipment reference asset. */
    EQUIPMENT;

}
