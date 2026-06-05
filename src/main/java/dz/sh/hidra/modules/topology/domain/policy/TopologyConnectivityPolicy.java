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
import dz.sh.hidra.modules.topology.domain.value.ConnectionType;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;

/**
 * Validates physical connectivity rules for the topology graph.
 *
 * <p>Business role:
 * This policy protects graph consistency for pipeline segments, topology connections, topology
 * nodes, and pipeline appurtenances.
 *
 * <p>Architecture role:
 * This is a pure domain policy. It must not be annotated as a Spring bean and must not access
 * repositories, persistence adapters, REST DTOs, identity implementation, organization implementation,
 * measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Segment and connection endpoints must be different. Pipeline segments must belong to their
 * pipeline. Appurtenance nodes must be node types suitable for appurtenances.
 *
 * <p>Usage:
 * Domain/application services may call this policy before persisting physical connectivity.
 */
public final class TopologyConnectivityPolicy {

    /**
     * Validates a pipeline segment graph edge.
     *
     * @param segment segment to validate
     */
    public void validateSegment(PipelineSegment segment) {
        Objects.requireNonNull(segment, "Pipeline segment must not be null.");

        if (segment.fromNodeId().equals(segment.toNodeId())) {
            throw new TopologyValidationException("Pipeline segment from node and to node must be different.");
        }
    }

    /**
     * Validates that a segment belongs to the expected pipeline.
     *
     * @param segment segment to validate
     * @param pipeline expected parent pipeline
     */
    public void validateSegmentBelongsToPipeline(PipelineSegment segment, Pipeline pipeline) {
        Objects.requireNonNull(segment, "Pipeline segment must not be null.");
        Objects.requireNonNull(pipeline, "Pipeline must not be null.");

        if (!segment.pipelineId().equals(pipeline.id())) {
            throw new TopologyValidationException("Pipeline segment must belong to the provided pipeline.");
        }
    }

    /**
     * Validates an explicit topology connection.
     *
     * @param connection connection to validate
     */
    public void validateConnection(TopologyConnection connection) {
        Objects.requireNonNull(connection, "Topology connection must not be null.");

        if (connection.fromNodeId().equals(connection.toNodeId())) {
            throw new TopologyValidationException("Topology connection from node and to node must be different.");
        }

        if (!linkedAssetMatchesConnectionType(connection)) {
            throw new TopologyValidationException("Topology connection linked asset type is inconsistent with connection type.");
        }
    }

    /**
     * Validates that an appurtenance is represented by the expected node.
     *
     * @param appurtenance appurtenance to validate
     * @param node topology node to validate
     */
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

    /**
     * Validates that a facility node is not used as a pipeline appurtenance node.
     *
     * @param node node to validate
     */
    public void rejectFacilityOnlyNodeForAppurtenance(TopologyNode node) {
        Objects.requireNonNull(node, "Topology node must not be null.");

        if (node.nodeType() == NodeType.FACILITY_INLET
                || node.nodeType() == NodeType.FACILITY_OUTLET
                || node.nodeType() == NodeType.FACILITY_INTERNAL) {

            throw new TopologyValidationException("Facility-only node type cannot represent a pipeline appurtenance.");
        }
    }

    private boolean linkedAssetMatchesConnectionType(TopologyConnection connection) {
        if (connection.connectionType() == ConnectionType.PIPELINE_SEGMENT) {
            return connection.linkedAssetType() == TopologyAssetType.SEGMENT;
        }

        if (connection.connectionType() == ConnectionType.FACILITY_INTERNAL) {
            return connection.linkedAssetType() == TopologyAssetType.FACILITY;
        }

        if (connection.connectionType() == ConnectionType.APPURTENANCE_CONNECTION
                || connection.connectionType() == ConnectionType.VALVE_CONNECTION
                || connection.connectionType() == ConnectionType.METERING_CONNECTION) {

            return connection.linkedAssetType() == TopologyAssetType.APPURTENANCE
                    || connection.linkedAssetType() == TopologyAssetType.EQUIPMENT;
        }

        return true;
    }

    private boolean isValidAppurtenanceNodeType(NodeType nodeType) {
        return switch (nodeType) {
            case PIPELINE_VALVE_POINT,
                    INJECTION_POINT,
                    EXTRACTION_POINT,
                    PURGE_POINT,
                    VENT_POINT,
                    DRAIN_POINT,
                    METERING_POINT,
                    SAMPLING_POINT,
                    SCRAPER_POINT,
                    CONNECTION_POINT,
                    PIPELINE_JUNCTION,
                    OTHER -> true;
            case FACILITY_INLET,
                    FACILITY_OUTLET,
                    FACILITY_INTERNAL,
                    RECEIPT_POINT,
                    DELIVERY_POINT -> false;
        };
    }
}
