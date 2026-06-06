/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectivityPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.policy
 *
 * @Description : Policy validating topology physical connectivity.
 *
 */
package dz.sh.hidra.modules.topology.domain.policy;

import java.util.Objects;

import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.NodeTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;

/**
 * Validates physical connectivity rules for the topology graph.
 *
 * <p>Business role:
 * This policy protects graph consistency for pipeline segments, topology connections, topology
 * nodes, and pipeline appurtenances.
 *
 * <p>Architecture role:
 * This policy evaluates configurable node/connection type references by stable language-neutral
 * codes, not by Java enum identity.
 *
 * <p>Validation:
 * Segment and connection endpoints must be different. Pipeline segments must belong to their
 * pipeline. Appurtenance nodes must be node types suitable for appurtenances.
 */
public final class TopologyConnectivityPolicy {

    public void validateSegment(PipelineSegment segment) {
        Objects.requireNonNull(segment, "Pipeline segment must not be null.");

        if (segment.fromNodeId().equals(segment.toNodeId())) {
            throw new TopologyValidationException("Pipeline segment from node and to node must be different.");
        }
    }

    public void validateSegmentBelongsToPipeline(PipelineSegment segment, Pipeline pipeline) {
        Objects.requireNonNull(segment, "Pipeline segment must not be null.");
        Objects.requireNonNull(pipeline, "Pipeline must not be null.");

        if (!segment.pipelineId().equals(pipeline.id())) {
            throw new TopologyValidationException("Pipeline segment must belong to the provided pipeline.");
        }
    }

    public void validateConnection(TopologyConnection connection) {
        Objects.requireNonNull(connection, "Topology connection must not be null.");

        if (connection.fromNodeId().equals(connection.toNodeId())) {
            throw new TopologyValidationException("Topology connection from node and to node must be different.");
        }

        if (!linkedAssetMatchesConnectionType(connection)) {
            throw new TopologyValidationException("Topology connection linked asset type is inconsistent with connection type.");
        }
    }

    public void validateAppurtenanceNode(PipelineAppurtenance appurtenance, TopologyNode node) {
        Objects.requireNonNull(appurtenance, "Pipeline appurtenance must not be null.");
        Objects.requireNonNull(node, "Topology node must not be null.");

        if (!appurtenance.nodeId().equals(node.id())) {
            throw new TopologyValidationException("Pipeline appurtenance node id must match the provided topology node.");
        }

        if (node.pipelineAppurtenanceId() != null && !node.pipelineAppurtenanceId().equals(appurtenance.id())) {
            throw new TopologyValidationException("Topology node appurtenance reference must match the provided appurtenance.");
        }

        if (!isValidAppurtenanceNodeType(node.nodeType())) {
            throw new TopologyValidationException("Topology node type is not valid for a pipeline appurtenance.");
        }
    }

    public void rejectFacilityOnlyNodeForAppurtenance(TopologyNode node) {
        Objects.requireNonNull(node, "Topology node must not be null.");

        if (node.nodeType().isAny("FACILITY_INLET", "FACILITY_OUTLET", "FACILITY_INTERNAL")) {
            throw new TopologyValidationException("Facility-only node type cannot represent a pipeline appurtenance.");
        }
    }

    private boolean linkedAssetMatchesConnectionType(TopologyConnection connection) {
        if (connection.connectionType().is("PIPELINE_SEGMENT")) {
            return connection.linkedAssetType() == TopologyAssetType.SEGMENT;
        }

        if (connection.connectionType().is("FACILITY_INTERNAL")) {
            return connection.linkedAssetType() == TopologyAssetType.FACILITY;
        }

        if (connection.connectionType().isAny("APPURTENANCE_CONNECTION", "VALVE_CONNECTION", "METERING_CONNECTION")) {
            return connection.linkedAssetType() == TopologyAssetType.APPURTENANCE
                    || connection.linkedAssetType() == TopologyAssetType.EQUIPMENT;
        }

        return true;
    }

    private boolean isValidAppurtenanceNodeType(NodeTypeReference nodeType) {
        return nodeType.isAny(
                "PIPELINE_VALVE_POINT",
                "INJECTION_POINT",
                "EXTRACTION_POINT",
                "PURGE_POINT",
                "VENT_POINT",
                "DRAIN_POINT",
                "METERING_POINT",
                "SAMPLING_POINT",
                "SCRAPER_POINT",
                "CONNECTION_POINT",
                "PIPELINE_JUNCTION",
                "OTHER");
    }
}
