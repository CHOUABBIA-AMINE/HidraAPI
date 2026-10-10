/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationPhysicalNetworkRevisionContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.simulation
 *
 * @Description : Exports exact immutable physical revisions using standard Java DTOs.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.simulation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** A verified digest establishes byte integrity, not approval or live scope eligibility. */
public interface SimulationPhysicalNetworkRevisionContract {
    Optional<Revision> find(String sourceId, String revisionId);

    record Revision(String sourceId, String revisionId, String scopeType, String scopeId,
                    Instant recordedAt, Instant effectiveFrom, Instant effectiveUntil, String origin,
                    String evidenceReference, List<Node> nodes, List<PipeSegment> pipeSegments,
                    List<EquipmentLink> equipmentLinks, String payloadFormat, String sha256) {
        public Revision {
            sourceId = identity(sourceId, "Source identity");
            revisionId = identity(revisionId, "Revision identity");
            oneOf(scopeType, "Scope type", "PIPELINE_SYSTEM", "PIPELINE");
            scopeId = identity(scopeId, "Scope identity");
            required(recordedAt, "Recorded time");
            required(effectiveFrom, "Effective start");
            if (effectiveUntil != null && !effectiveUntil.isAfter(effectiveFrom)) {
                throw new IllegalArgumentException("Effective end must follow effective start.");
            }
            oneOf(origin, "Origin", "DECLARED_PARAMETER", "SYNTHETIC");
            evidenceReference = identity(evidenceReference, "Evidence reference");
            nodes = immutable(nodes);
            pipeSegments = immutable(pipeSegments);
            equipmentLinks = immutable(equipmentLinks);
            if (nodes.size() < 2 || pipeSegments.isEmpty()) {
                throw new IllegalArgumentException("Physical revision requires two nodes and a real pipe.");
            }
            if (!"HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1".equals(payloadFormat)
                    || sha256 == null || !sha256.matches("[0-9a-f]{64}")) {
                throw new IllegalArgumentException("Unsupported physical revision format or digest.");
            }
        }
    }

    record Node(String id, BigDecimal elevationMeters) {
        public Node {
            id = identity(id, "Node identity");
            required(elevationMeters, "Elevation in meters");
        }
    }

    record PipeSegment(String id, String fromNodeId, String toNodeId, BigDecimal lengthMeters,
                       BigDecimal internalDiameterMeters, BigDecimal absoluteRoughnessMeters) {
        public PipeSegment {
            id = identity(id, "Pipe identity");
            fromNodeId = identity(fromNodeId, "Pipe from node");
            toNodeId = identity(toNodeId, "Pipe to node");
            distinct(fromNodeId, toNodeId);
            positive(lengthMeters, "Length in meters");
            positive(internalDiameterMeters, "Internal diameter in meters");
            required(absoluteRoughnessMeters, "Roughness in meters");
            if (absoluteRoughnessMeters.signum() < 0) {
                throw new IllegalArgumentException("Roughness must be nonnegative.");
            }
        }
    }

    record EquipmentLink(String id, String fromNodeId, String toNodeId, String kind) {
        public EquipmentLink {
            id = identity(id, "Equipment identity");
            fromNodeId = identity(fromNodeId, "Equipment from node");
            toNodeId = identity(toNodeId, "Equipment to node");
            distinct(fromNodeId, toNodeId);
            oneOf(kind, "Equipment kind", "COMPRESSOR", "VALVE");
        }
    }

    private static String identity(String value, String field) {
        if (value == null || value.isBlank() || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank.");
        }
        return value.trim();
    }

    private static void required(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " must be supplied.");
        }
    }

    private static void positive(BigDecimal value, String field) {
        required(value, field);
        if (value.signum() <= 0) {
            throw new IllegalArgumentException(field + " must be positive.");
        }
    }

    private static void distinct(String from, String to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("Link endpoints must be distinct.");
        }
    }

    private static void oneOf(String value, String field, String first, String second) {
        if (!first.equals(value) && !second.equals(value)) {
            throw new IllegalArgumentException(field + " has an unsupported enum name.");
        }
    }

    private static <T> List<T> immutable(List<T> values) {
        if (values == null || values.stream().anyMatch(value -> value == null)) {
            throw new IllegalArgumentException("Physical revision lists require nonnull entries.");
        }
        return List.copyOf(values);
    }
}
