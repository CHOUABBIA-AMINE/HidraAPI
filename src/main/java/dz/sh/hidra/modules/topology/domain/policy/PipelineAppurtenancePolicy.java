/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenancePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.policy
 *
 * @Description : Policy validating pipeline appurtenance rules.
 *
 */
package dz.sh.hidra.modules.topology.domain.policy;

import java.util.Objects;

import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.ValveType;

/**
 * Validates pipeline appurtenance rules.
 *
 * <p>Business role:
 * This policy makes valves, injection points, purge points, extraction points, vents, drains,
 * scraper points, hot taps, bypass points, and other point assets explicit topology concepts.
 *
 * <p>Architecture role:
 * This is a pure domain policy. It must not be annotated as a Spring bean and must not access
 * repositories, persistence adapters, REST DTOs, identity implementation, organization implementation,
 * measurement, operations, flow, risk, workflow, maintenance, or infrastructure code.
 *
 * <p>Validation:
 * Valve type is required when appurtenance type is VALVE and forbidden for all non-valve
 * appurtenances. Every appurtenance must belong to a pipeline and must reference a topology node.
 *
 * <p>Usage:
 * Domain/application services may call this policy before registering pipeline point assets.
 */
public final class PipelineAppurtenancePolicy {

    /**
     * Validates appurtenance invariants.
     *
     * @param appurtenance appurtenance to validate
     */
    public void validate(PipelineAppurtenance appurtenance) {
        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");

        validateValveTypeConsistency(appurtenance.appurtenanceType(), appurtenance.valveType());

        if (appurtenance.pipelineId() == null) {
            throw new TopologyValidationException("Pipeline appurtenance must reference a pipeline.");
        }

        if (appurtenance.nodeId() == null) {
            throw new TopologyValidationException("Pipeline appurtenance must reference a topology node.");
        }

        if (appurtenance.pipelineKilometerPoint() == null) {
            throw new TopologyValidationException("Pipeline appurtenance must define a pipeline kilometer point.");
        }
    }

    /**
     * Validates appurtenance valve type consistency.
     *
     * @param appurtenanceType appurtenance type
     * @param valveType valve type
     */
    public void validateValveTypeConsistency(PipelineAppurtenanceType appurtenanceType, ValveType valveType) {
        Objects.requireNonNull(appurtenanceType, "Pipeline appurtenance type must not be null.");

        if (appurtenanceType.isValve() && valveType == null) {
            throw new TopologyValidationException("Valve type is required when appurtenance type is VALVE.");
        }

        if (!appurtenanceType.isValve() && valveType != null) {
            throw new TopologyValidationException("Valve type must be null when appurtenance type is not VALVE.");
        }
    }

    /**
     * Validates that an appurtenance belongs to the expected pipeline.
     *
     * @param appurtenance appurtenance to validate
     * @param pipeline expected parent pipeline
     */
    public void validateBelongsToPipeline(PipelineAppurtenance appurtenance, Pipeline pipeline) {
        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");
        Objects.requireNonNull(pipeline, "Pipeline must not be null.");

        if (!appurtenance.pipelineId().equals(pipeline.id())) {
            throw new TopologyValidationException("Pipeline appurtenance must belong to the provided pipeline.");
        }
    }

    /**
     * Validates that an appurtenance has a compatible topology node.
     *
     * @param appurtenance appurtenance to validate
     * @param node topology node to validate
     */
    public void validateNodeCompatibility(PipelineAppurtenance appurtenance, TopologyNode node) {
        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");
        Objects.requireNonNull(node, "Topology node must not be null.");

        if (!appurtenance.nodeId().equals(node.id())) {
            throw new TopologyValidationException("Pipeline appurtenance node id must match the provided topology node.");
        }

        if (!nodeTypeMatchesAppurtenanceType(appurtenance.appurtenanceType(), node.nodeType())) {
            throw new TopologyValidationException("Topology node type is inconsistent with pipeline appurtenance type.");
        }
    }

    private boolean nodeTypeMatchesAppurtenanceType(PipelineAppurtenanceType appurtenanceType, NodeType nodeType) {
        return switch (appurtenanceType) {
            case VALVE -> nodeType == NodeType.PIPELINE_VALVE_POINT || nodeType == NodeType.CONNECTION_POINT || nodeType == NodeType.OTHER;
            case INJECTION_POINT -> nodeType == NodeType.INJECTION_POINT || nodeType == NodeType.CONNECTION_POINT || nodeType == NodeType.OTHER;
            case EXTRACTION_POINT -> nodeType == NodeType.EXTRACTION_POINT || nodeType == NodeType.CONNECTION_POINT || nodeType == NodeType.OTHER;
            case PURGE_POINT -> nodeType == NodeType.PURGE_POINT || nodeType == NodeType.CONNECTION_POINT || nodeType == NodeType.OTHER;
            case VENT_POINT -> nodeType == NodeType.VENT_POINT || nodeType == NodeType.CONNECTION_POINT || nodeType == NodeType.OTHER;
            case DRAIN_POINT -> nodeType == NodeType.DRAIN_POINT || nodeType == NodeType.CONNECTION_POINT || nodeType == NodeType.OTHER;
            case SAMPLING_POINT -> nodeType == NodeType.SAMPLING_POINT || nodeType == NodeType.CONNECTION_POINT || nodeType == NodeType.OTHER;
            case METERING_POINT -> nodeType == NodeType.METERING_POINT || nodeType == NodeType.CONNECTION_POINT || nodeType == NodeType.OTHER;
            case SCRAPER_LAUNCHER, SCRAPER_RECEIVER -> nodeType == NodeType.SCRAPER_POINT || nodeType == NodeType.CONNECTION_POINT || nodeType == NodeType.OTHER;
            case HOT_TAP_POINT, BYPASS_POINT, CONNECTION_POINT -> nodeType == NodeType.CONNECTION_POINT || nodeType == NodeType.PIPELINE_JUNCTION || nodeType == NodeType.OTHER;
            case OTHER -> true;
        };
    }
}
