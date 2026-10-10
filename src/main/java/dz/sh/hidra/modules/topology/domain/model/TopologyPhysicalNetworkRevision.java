/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPhysicalNetworkRevision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Preserves an immutable physical source revision with explicit SI values and equipment incidence.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Connectivity and declared provenance do not establish operating feasibility or approval. */
public record TopologyPhysicalNetworkRevision(
        String sourceId,
        String revisionId,
        ScopeType scopeType,
        String scopeId,
        Instant recordedAt,
        Instant effectiveFrom,
        Instant effectiveUntil,
        Origin origin,
        String evidenceReference,
        List<Node> nodes,
        List<PipeSegment> pipeSegments,
        List<EquipmentLink> equipmentLinks
) {
    public enum ScopeType { PIPELINE_SYSTEM, PIPELINE }

    /** DECLARED_PARAMETER is a supplied declaration, not verified or approved field data. */
    public enum Origin { DECLARED_PARAMETER, SYNTHETIC }

    public enum EquipmentKind { COMPRESSOR, VALVE, REGULATOR }

    public TopologyPhysicalNetworkRevision {
        sourceId = identity(sourceId, "Source identity");
        revisionId = identity(revisionId, "Revision identity");
        required(scopeType, "Scope type");
        scopeId = identity(scopeId, "Scope identity");
        required(recordedAt, "Recorded time");
        required(effectiveFrom, "Effective start");
        if (effectiveUntil != null && !effectiveUntil.isAfter(effectiveFrom)) {
            throw invalid("Effective end must be strictly after effective start.");
        }
        required(origin, "Origin");
        evidenceReference = identity(evidenceReference, "Evidence reference");
        nodes = immutable(nodes, "Nodes");
        pipeSegments = immutable(pipeSegments, "Pipe segments");
        equipmentLinks = immutable(equipmentLinks, "Equipment links");
        if (nodes.size() < 2 || pipeSegments.isEmpty()) {
            throw invalid("Physical network requires at least two nodes and one real pipe.");
        }
        Map<String, Set<String>> adjacency = new HashMap<>();
        for (Node node : nodes) {
            if (adjacency.putIfAbsent(node.id(), new HashSet<>()) != null) {
                throw invalid("Duplicate node identity: " + node.id());
            }
        }
        Set<String> linkIds = new HashSet<>();
        for (PipeSegment pipe : pipeSegments) {
            connect(adjacency, linkIds, pipe.id(), pipe.fromNodeId(), pipe.toNodeId());
        }
        for (EquipmentLink equipment : equipmentLinks) {
            connect(adjacency, linkIds, equipment.id(), equipment.fromNodeId(), equipment.toNodeId());
        }
        if (adjacency.values().stream().anyMatch(Set::isEmpty)) {
            throw invalid("Physical network must not contain isolated nodes.");
        }
        Set<String> visited = new HashSet<>();
        var pending = new ArrayDeque<String>();
        pending.add(nodes.get(0).id());
        visited.add(nodes.get(0).id());
        while (!pending.isEmpty()) {
            for (String neighbor : adjacency.get(pending.removeFirst())) {
                if (visited.add(neighbor)) {
                    pending.addLast(neighbor);
                }
            }
        }
        if (visited.size() != nodes.size()) {
            throw invalid("Physical network must form one undirected connected component.");
        }
    }

    public boolean effectiveAt(Instant at) {
        required(at, "Effective query time");
        return !at.isBefore(effectiveFrom) && (effectiveUntil == null || at.isBefore(effectiveUntil));
    }

    public record Node(String id, BigDecimal elevationMeters) {
        public Node {
            id = identity(id, "Node identity");
            required(elevationMeters, "Elevation in meters");
        }
    }

    public record PipeSegment(String id, String fromNodeId, String toNodeId,
                              BigDecimal lengthMeters, BigDecimal internalDiameterMeters,
                              BigDecimal absoluteRoughnessMeters) {
        public PipeSegment {
            id = identity(id, "Pipe identity");
            fromNodeId = identity(fromNodeId, "Pipe from node");
            toNodeId = identity(toNodeId, "Pipe to node");
            distinct(fromNodeId, toNodeId);
            positive(lengthMeters, "Length in meters");
            positive(internalDiameterMeters, "Internal diameter in meters");
            required(absoluteRoughnessMeters, "Absolute roughness in meters");
            if (absoluteRoughnessMeters.signum() < 0) {
                throw invalid("Absolute roughness must be nonnegative.");
            }
        }
    }

    /** Incidence only: no equipment law, curve, operating state or approval is supplied. */
    public record EquipmentLink(String id, String fromNodeId, String toNodeId, EquipmentKind kind) {
        public EquipmentLink {
            id = identity(id, "Equipment identity");
            fromNodeId = identity(fromNodeId, "Equipment from node");
            toNodeId = identity(toNodeId, "Equipment to node");
            distinct(fromNodeId, toNodeId);
            required(kind, "Equipment kind");
        }
    }

    private static void connect(Map<String, Set<String>> adjacency, Set<String> ids,
                                String id, String from, String to) {
        if (!ids.add(id)) {
            throw invalid("Duplicate or colliding link identity: " + id);
        }
        if (!adjacency.containsKey(from) || !adjacency.containsKey(to)) {
            throw invalid("Link endpoints must reference nodes in this revision.");
        }
        adjacency.get(from).add(to);
        adjacency.get(to).add(from);
    }

    private static void distinct(String from, String to) {
        if (from.equals(to)) {
            throw invalid("Link endpoints must be distinct.");
        }
    }

    private static void positive(BigDecimal value, String field) {
        required(value, field);
        if (value.signum() <= 0) {
            throw invalid(field + " must be positive.");
        }
    }

    private static String identity(String value, String field) {
        if (value == null || value.isBlank() || value.trim().isEmpty()) {
            throw invalid(field + " must not be blank.");
        }
        return value.trim();
    }

    private static void required(Object value, String field) {
        if (value == null) {
            throw invalid(field + " must be supplied.");
        }
    }

    private static <T> List<T> immutable(List<T> values, String field) {
        if (values == null || values.stream().anyMatch(value -> value == null)) {
            throw invalid(field + " must be supplied without null entries.");
        }
        return List.copyOf(values);
    }

    private static InvalidTopologyValueException invalid(String message) {
        return new InvalidTopologyValueException(message);
    }
}
