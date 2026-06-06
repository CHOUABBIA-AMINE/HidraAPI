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
import dz.sh.hidra.modules.topology.domain.value.NodeTypeReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceTypeReference;
import dz.sh.hidra.modules.topology.domain.value.ValveType;
import dz.sh.hidra.modules.topology.domain.value.ValveTypeReference;

/**
 * Validates pipeline appurtenance rules.
 *
 * <p>Business role:
 * This policy makes valves, injection points, purge points, extraction points, vents, drains,
 * scraper points, hot taps, bypass points, and other point assets explicit topology concepts.
 *
 * <p>Architecture role:
 * This policy evaluates appurtenance and node type catalog references by stable language-neutral
 * codes, not by Java enum identity.
 *
 * <p>Validation:
 * Valve type is required when appurtenance type is VALVE and forbidden for all non-valve
 * appurtenances. Every appurtenance must belong to a pipeline and must reference a topology node.
 */
public final class PipelineAppurtenancePolicy {

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

    public void validateValveTypeConsistency(
            PipelineAppurtenanceTypeReference appurtenanceType,
            ValveTypeReference valveType) {

        Objects.requireNonNull(appurtenanceType, "Pipeline appurtenance type reference must not be null.");

        if (appurtenanceType.isValve() && valveType == null) {
            throw new TopologyValidationException("Valve type is required when appurtenance type is VALVE.");
        }

        if (!appurtenanceType.isValve() && valveType != null) {
            throw new TopologyValidationException("Valve type must be null when appurtenance type is not VALVE.");
        }
    }

    @Deprecated(forRemoval = true)
    public void validateValveTypeConsistency(PipelineAppurtenanceType appurtenanceType, ValveType valveType) {
        validateValveTypeConsistency(
                PipelineAppurtenanceTypeReference.from(appurtenanceType),
                ValveTypeReference.from(valveType));
    }

    public void validateBelongsToPipeline(PipelineAppurtenance appurtenance, Pipeline pipeline) {
        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");
        Objects.requireNonNull(pipeline, "Pipeline must not be null.");

        if (!appurtenance.pipelineId().equals(pipeline.id())) {
            throw new TopologyValidationException("Pipeline appurtenance must belong to the provided pipeline.");
        }
    }

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

    private boolean nodeTypeMatchesAppurtenanceType(
            PipelineAppurtenanceTypeReference appurtenanceType,
            NodeTypeReference nodeType) {

        if (appurtenanceType.is("VALVE")) {
            return nodeType.isAny("PIPELINE_VALVE_POINT", "CONNECTION_POINT", "OTHER");
        }
        if (appurtenanceType.is("INJECTION_POINT")) {
            return nodeType.isAny("INJECTION_POINT", "CONNECTION_POINT", "OTHER");
        }
        if (appurtenanceType.is("EXTRACTION_POINT")) {
            return nodeType.isAny("EXTRACTION_POINT", "CONNECTION_POINT", "OTHER");
        }
        if (appurtenanceType.is("PURGE_POINT")) {
            return nodeType.isAny("PURGE_POINT", "CONNECTION_POINT", "OTHER");
        }
        if (appurtenanceType.is("VENT_POINT")) {
            return nodeType.isAny("VENT_POINT", "CONNECTION_POINT", "OTHER");
        }
        if (appurtenanceType.is("DRAIN_POINT")) {
            return nodeType.isAny("DRAIN_POINT", "CONNECTION_POINT", "OTHER");
        }
        if (appurtenanceType.is("SAMPLING_POINT")) {
            return nodeType.isAny("SAMPLING_POINT", "CONNECTION_POINT", "OTHER");
        }
        if (appurtenanceType.is("METERING_POINT")) {
            return nodeType.isAny("METERING_POINT", "CONNECTION_POINT", "OTHER");
        }
        if (appurtenanceType.isAny("SCRAPER_LAUNCHER", "SCRAPER_RECEIVER")) {
            return nodeType.isAny("SCRAPER_POINT", "CONNECTION_POINT", "OTHER");
        }
        if (appurtenanceType.isAny("HOT_TAP_POINT", "BYPASS_POINT", "CONNECTION_POINT")) {
            return nodeType.isAny("CONNECTION_POINT", "PIPELINE_JUNCTION", "OTHER");
        }
        return appurtenanceType.is("OTHER");
    }
}
