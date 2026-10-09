/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationConnectedNetworkInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Captures a structurally connected immutable graph of real pipe and equipment links.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Structural union connectivity does not establish active hydraulic connectivity or solver readiness. */
public record SimulationConnectedNetworkInput(
        String id,
        String scopeType,
        String scopeId,
        SimulationInputSourceVersion sourceVersion,
        List<SimulationNetworkNodeInput> nodes,
        List<SimulationPipeSegmentInput> pipeSegments,
        SimulationEquipmentModelInput equipmentModel
) {
    public SimulationConnectedNetworkInput {
        id = required(id, "Network identity");
        scopeType = required(scopeType, "Network scope type");
        scopeId = required(scopeId, "Network scope identity");
        if (!Set.of("PIPELINE_SYSTEM", "PIPELINE", "SEGMENT_GROUP", "FACILITY_NETWORK").contains(scopeType)) {
            throw new InvalidSimulationValueException("Unsupported network scope type.");
        }
        if (sourceVersion == null || sourceVersion.kind() != SourceKind.TOPOLOGY_CONFIGURATION || equipmentModel == null) {
            throw new InvalidSimulationValueException("Connected network requires topology source and explicit equipment model.");
        }
        nodes = immutable(nodes, "Nodes");
        pipeSegments = immutable(pipeSegments, "Pipe segments");
        if (nodes.size() < 2 || pipeSegments.isEmpty()) {
            throw new InvalidSimulationValueException("Connected network requires at least two nodes and one real pipe.");
        }
        Map<String, Set<String>> adjacency = new HashMap<>();
        for (var node : nodes) {
            if (adjacency.putIfAbsent(node.id(), new HashSet<>()) != null) {
                throw new InvalidSimulationValueException("Duplicate network node identity.");
            }
        }
        var linkIds = new HashSet<String>();
        for (var pipe : pipeSegments) {
            if (!linkIds.add(pipe.id())) {
                throw new InvalidSimulationValueException("Duplicate network link identity.");
            }
            connect(adjacency, pipe.fromNodeId(), pipe.toNodeId());
        }
        for (var equipment : equipmentModel.equipment()) {
            if (!linkIds.add(equipment.id())) {
                throw new InvalidSimulationValueException("Pipe and equipment link identities must be disjoint.");
            }
            connect(adjacency, equipment.fromNodeId(), equipment.toNodeId());
        }
        if (adjacency.values().stream().anyMatch(Set::isEmpty)) {
            throw new InvalidSimulationValueException("Connected network must not contain isolated nodes.");
        }
        var visited = new HashSet<String>();
        var pending = new ArrayDeque<String>();
        visited.add(nodes.get(0).id());
        pending.add(nodes.get(0).id());
        while (!pending.isEmpty()) {
            for (var neighbor : adjacency.get(pending.removeFirst())) {
                if (visited.add(neighbor)) {
                    pending.addLast(neighbor);
                }
            }
        }
        if (visited.size() != nodes.size()) {
            throw new InvalidSimulationValueException("Pipe and equipment union must form one connected component.");
        }
    }

    private static void connect(Map<String, Set<String>> adjacency, String from, String to) {
        if (!adjacency.containsKey(from) || !adjacency.containsKey(to)) {
            throw new InvalidSimulationValueException("Every pipe and equipment endpoint must reference network nodes.");
        }
        adjacency.get(from).add(to);
        adjacency.get(to).add(from);
    }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidSimulationValueException(field + " must not be blank.");
        }
        return value.trim();
    }

    private static <T> List<T> immutable(List<T> values, String field) {
        if (values == null || values.stream().anyMatch(value -> value == null)) {
            throw new InvalidSimulationValueException(field + " must be present without null entries.");
        }
        return List.copyOf(values);
    }
}
