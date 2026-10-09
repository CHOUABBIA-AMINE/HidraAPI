/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationPhysicalNetworkInput
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Captures an immutable connected pipe graph pinned to a topology revision.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Pipe-only contract: does not establish complete equipment modeling or solver readiness. */
public record SimulationPhysicalNetworkInput(
        String id,
        String topologyRevisionId,
        List<SimulationNetworkNodeInput> nodes,
        List<SimulationPipeSegmentInput> pipeSegments
) {
    public SimulationPhysicalNetworkInput {
        id = required(id, "Network identity");
        topologyRevisionId = required(topologyRevisionId, "Topology revision identity");
        nodes = immutable(nodes, "Nodes");
        pipeSegments = immutable(pipeSegments, "Pipe segments");
        if (nodes.size() < 2 || pipeSegments.isEmpty()) {
            throw new InvalidSimulationValueException("Network requires at least two nodes and one pipe.");
        }
        Map<String, Set<String>> adjacency = new HashMap<>();
        for (var node : nodes) {
            if (adjacency.putIfAbsent(node.id(), new HashSet<>()) != null) {
                throw new InvalidSimulationValueException("Duplicate node identity: " + node.id());
            }
        }
        Set<String> pipeIds = new HashSet<>();
        for (var pipe : pipeSegments) {
            if (!pipeIds.add(pipe.id())) {
                throw new InvalidSimulationValueException("Duplicate pipe identity: " + pipe.id());
            }
            if (!adjacency.containsKey(pipe.fromNodeId()) || !adjacency.containsKey(pipe.toNodeId())) {
                throw new InvalidSimulationValueException("Pipe endpoints must reference network nodes.");
            }
            adjacency.get(pipe.fromNodeId()).add(pipe.toNodeId());
            adjacency.get(pipe.toNodeId()).add(pipe.fromNodeId());
        }
        if (adjacency.values().stream().anyMatch(Set::isEmpty)) {
            throw new InvalidSimulationValueException("Network must not contain isolated nodes.");
        }
        Set<String> visited = new HashSet<>();
        var pending = new ArrayDeque<String>();
        pending.add(nodes.get(0).id());
        visited.add(nodes.get(0).id());
        while (!pending.isEmpty()) {
            for (var neighbor : adjacency.get(pending.removeFirst())) {
                if (visited.add(neighbor)) {
                    pending.addLast(neighbor);
                }
            }
        }
        if (visited.size() != nodes.size()) {
            throw new InvalidSimulationValueException("Network must form one undirected connected component.");
        }
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
