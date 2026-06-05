/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectivityDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Domain service validating topology graph connectivity.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;

/**
 * Validates and coordinates physical graph connectivity.
 *
 * <p>Business role:
 * This service handles multi-node consistency checks for physical network connectivity. It validates
 * that segments and connections reference the expected graph vertices and helps detect duplicate
 * node usage in topology operations.
 *
 * <p>Architecture role:
 * This is a pure topology domain service. It has no Spring annotation, no repository access, no
 * persistence dependency, no REST DTO dependency, and no identity or organization implementation
 * dependency.
 *
 * <p>Validation:
 * From-node and to-node references must match supplied nodes and must be different. Connectivity
 * collections must not contain duplicate node identifiers when uniqueness is required.
 *
 * <p>Usage:
 * Application services should call this service when registering or validating network graph edges.
 */
public final class TopologyConnectivityDomainService {

    private final TopologyConnectivityPolicy connectivityPolicy;

    /**
     * Creates a service with the default connectivity policy.
     */
    public TopologyConnectivityDomainService() {
        this(new TopologyConnectivityPolicy());
    }

    /**
     * Creates a service with an explicit connectivity policy.
     *
     * @param connectivityPolicy connectivity policy
     */
    public TopologyConnectivityDomainService(TopologyConnectivityPolicy connectivityPolicy) {
        this.connectivityPolicy = Objects.requireNonNull(connectivityPolicy, "Topology connectivity policy must not be null.");
    }

    /**
     * Validates segment connectivity against supplied endpoint nodes.
     *
     * @param segment segment to validate
     * @param fromNode expected from-node
     * @param toNode expected to-node
     */
    public void validateSegmentConnectivity(
            PipelineSegment segment,
            TopologyNode fromNode,
            TopologyNode toNode) {

        Objects.requireNonNull(segment, "Pipeline segment must not be null.");
        Objects.requireNonNull(fromNode, "Pipeline segment from-node must not be null.");
        Objects.requireNonNull(toNode, "Pipeline segment to-node must not be null.");

        connectivityPolicy.validateSegment(segment);
        ensureDifferentNodes(fromNode.id(), toNode.id(), "Pipeline segment endpoint nodes must be different.");

        if (!segment.fromNodeId().equals(fromNode.id())) {
            throw new TopologyValidationException("Pipeline segment from-node id must match the provided from-node.");
        }

        if (!segment.toNodeId().equals(toNode.id())) {
            throw new TopologyValidationException("Pipeline segment to-node id must match the provided to-node.");
        }
    }

    /**
     * Validates explicit connection connectivity against supplied endpoint nodes.
     *
     * @param connection connection to validate
     * @param fromNode expected from-node
     * @param toNode expected to-node
     */
    public void validateConnectionConnectivity(
            TopologyConnection connection,
            TopologyNode fromNode,
            TopologyNode toNode) {

        Objects.requireNonNull(connection, "Topology connection must not be null.");
        Objects.requireNonNull(fromNode, "Topology connection from-node must not be null.");
        Objects.requireNonNull(toNode, "Topology connection to-node must not be null.");

        connectivityPolicy.validateConnection(connection);
        ensureDifferentNodes(fromNode.id(), toNode.id(), "Topology connection endpoint nodes must be different.");

        if (!connection.fromNodeId().equals(fromNode.id())) {
            throw new TopologyValidationException("Topology connection from-node id must match the provided from-node.");
        }

        if (!connection.toNodeId().equals(toNode.id())) {
            throw new TopologyValidationException("Topology connection to-node id must match the provided to-node.");
        }
    }

    /**
     * Ensures a set of topology nodes contains no duplicate identifiers.
     *
     * @param nodes nodes to validate
     */
    public void ensureUniqueNodeIds(Collection<TopologyNode> nodes) {
        Objects.requireNonNull(nodes, "Topology nodes collection must not be null.");

        Set<TopologyNodeId> seenNodeIds = new HashSet<>();

        for (TopologyNode node : nodes) {
            Objects.requireNonNull(node, "Topology nodes collection must not contain null elements.");

            if (!seenNodeIds.add(node.id())) {
                throw new TopologyValidationException("Topology nodes collection contains a duplicate node identifier.");
            }
        }
    }

    /**
     * Ensures two node identifiers are different.
     *
     * @param fromNodeId first node identifier
     * @param toNodeId second node identifier
     * @param message validation message
     */
    public void ensureDifferentNodes(TopologyNodeId fromNodeId, TopologyNodeId toNodeId, String message) {
        Objects.requireNonNull(fromNodeId, "From node id must not be null.");
        Objects.requireNonNull(toNodeId, "To node id must not be null.");

        if (fromNodeId.equals(toNodeId)) {
            throw new TopologyValidationException(message);
        }
    }
}
