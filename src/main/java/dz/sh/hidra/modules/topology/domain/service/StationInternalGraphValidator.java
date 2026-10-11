/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Validates directed station graph topology without numerical execution.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;
import java.util.*;

/** Diagnostic validation: drafts may have gaps, contradictions are always invalid. */
public final class StationInternalGraphValidator {
    public record Report(List<String> diagnostics, List<String> seriesPairs, List<String> parallelPairs) {
        public Report {
            diagnostics = List.copyOf(diagnostics);
            seriesPairs = List.copyOf(seriesPairs);
            parallelPairs = List.copyOf(parallelPairs);
        }
        public boolean complete() { return diagnostics.isEmpty(); }
    }

    public Report inspect(StationInternalGraph graph) {
        if (graph == null) throw invalid("Station graph required.");
        var roles = new HashMap<String, PortRole>();
        var types = new HashMap<String, HeaderKind>();
        var adjacency = new HashMap<String, Set<String>>();
        for (var h : graph.headers()) { roles.put(h.portId(), PortRole.HEADER); types.put(h.portId(), h.kind()); }
        for (var u : graph.units()) {
            roles.put(u.inletPortId(), PortRole.UNIT_INLET);
            roles.put(u.outletPortId(), PortRole.UNIT_OUTLET);
            adjacency.computeIfAbsent(u.inletPortId(), k -> new HashSet<>()).add(u.outletPortId());
        }
        var diagnostics = new TreeSet<String>();
        var edgePairs = new HashSet<String>();
        for (var e : graph.edges()) {
            if (!roles.containsKey(e.fromPortId()) || !roles.containsKey(e.toPortId())) {
                diagnostics.add("DANGLING_PORT:" + e.id());
                continue;
            }
            if (roles.get(e.fromPortId()) == PortRole.UNIT_INLET
                    || roles.get(e.toPortId()) == PortRole.UNIT_OUTLET
                    || types.get(e.fromPortId()) == HeaderKind.DISCHARGE
                    || types.get(e.toPortId()) == HeaderKind.SUCTION) {
                throw invalid("Reversed or unsupported port incidence: " + e.id());
            }
            if (!edgePairs.add(e.fromPortId() + "\u0000" + e.toPortId())) throw invalid("Duplicate directed edge.");
            adjacency.computeIfAbsent(e.fromPortId(), k -> new HashSet<>()).add(e.toPortId());
        }
        if (cyclic(adjacency)) throw invalid("Directed station graph cycle.");
        if (graph.headers().stream().noneMatch(h -> h.kind() == HeaderKind.SUCTION)) diagnostics.add("MISSING_SUCTION_HEADER");
        if (graph.headers().stream().noneMatch(h -> h.kind() == HeaderKind.DISCHARGE)) diagnostics.add("MISSING_DISCHARGE_HEADER");
        for (var u : graph.units()) {
            if (!graph.edges().stream().anyMatch(e -> e.toPortId().equals(u.inletPortId())))
                diagnostics.add("UNCONNECTED_INLET:" + u.equipmentId());
            if (!graph.edges().stream().anyMatch(e -> e.fromPortId().equals(u.outletPortId())))
                diagnostics.add("UNCONNECTED_OUTLET:" + u.equipmentId());
        }
        var series = new TreeSet<String>();
        var parallel = new TreeSet<String>();
        for (int i = 0; i < graph.units().size(); i++) for (int j = i + 1; j < graph.units().size(); j++) {
            var a = graph.units().get(i); var b = graph.units().get(j);
            if (reachable(adjacency, a.outletPortId(), b.inletPortId())) series.add(a.equipmentId() + ">" + b.equipmentId());
            if (reachable(adjacency, b.outletPortId(), a.inletPortId())) series.add(b.equipmentId() + ">" + a.equipmentId());
            if (commonPredecessor(graph, a, b) && commonSuccessor(graph, a, b))
                parallel.add(a.equipmentId() + "|" + b.equipmentId());
        }
        if (graph.status() == Status.COMPLETE && !diagnostics.isEmpty())
            throw invalid("Complete graph has missing evidence: " + diagnostics);
        return new Report(List.copyOf(diagnostics), List.copyOf(series), List.copyOf(parallel));
    }

    private boolean commonPredecessor(StationInternalGraph g, Unit a, Unit b) {
        return g.headers().stream().anyMatch(h -> reachableFromEdges(g, h.portId(), a.inletPortId())
                && reachableFromEdges(g, h.portId(), b.inletPortId()));
    }
    private boolean commonSuccessor(StationInternalGraph g, Unit a, Unit b) {
        return g.headers().stream().anyMatch(h -> reachableFromEdges(g, a.outletPortId(), h.portId())
                && reachableFromEdges(g, b.outletPortId(), h.portId()));
    }
    /** Branch detection uses declared external edges only, never inferred travel through another unit. */
    private boolean reachableFromEdges(StationInternalGraph g, String from, String to) {
        var adjacency = new HashMap<String, Set<String>>();
        for (var e : g.edges()) adjacency.computeIfAbsent(e.fromPortId(), k -> new HashSet<>()).add(e.toPortId());
        return reachable(adjacency, from, to);
    }
    private boolean reachable(Map<String, Set<String>> adj, String start, String target) {
        if (start.equals(target)) return true;
        var seen = new HashSet<String>();
        var queue = new ArrayDeque<String>(); queue.add(start);
        while (!queue.isEmpty()) {
            var at = queue.removeFirst(); if (!seen.add(at)) continue;
            for (var next : adj.getOrDefault(at, Set.of())) {
                if (next.equals(target)) return true;
                if (!seen.contains(next)) queue.add(next);
            }
        }
        return false;
    }
    private boolean cyclic(Map<String, Set<String>> adj) {
        var visiting = new HashSet<String>(); var visited = new HashSet<String>();
        for (var node : adj.keySet()) if (cycle(node, adj, visiting, visited)) return true;
        return false;
    }
    private boolean cycle(String node, Map<String, Set<String>> adj, Set<String> visiting, Set<String> visited) {
        if (visited.contains(node)) return false;
        if (!visiting.add(node)) return true;
        for (var neighbor : adj.getOrDefault(node, Set.of()))
            if (cycle(neighbor, adj, visiting, visited)) return true;
        visiting.remove(node); visited.add(node);
        return false;
    }
    private InvalidTopologyValueException invalid(String message) { return new InvalidTopologyValueException(message); }
}
