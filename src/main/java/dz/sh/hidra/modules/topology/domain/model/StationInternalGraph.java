/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraph
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Captures an independently identified declarative station header/port/unit graph.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Source declaration only: no physical flow, operational state or persisted publication. */
public record StationInternalGraph(String facilityId, String revisionId, Status status,
        List<Header> headers, List<Unit> units, List<Edge> edges) {
    public enum Status { DRAFT, COMPLETE }
    public enum HeaderKind { SUCTION, DISCHARGE, INTERMEDIATE }
    public enum PortRole { HEADER, UNIT_INLET, UNIT_OUTLET }

    public record Header(String id, HeaderKind kind, String portId) {
        public Header {
            id = required(id, "Header identity");
            portId = required(portId, "Header port");
            if (kind == null) throw invalid("Header kind required.");
        }
    }

    public record Unit(String equipmentId, String facilityId, String inletPortId, String outletPortId) {
        public Unit {
            equipmentId = required(equipmentId, "Installed equipment identity");
            facilityId = required(facilityId, "Equipment station identity");
            inletPortId = required(inletPortId, "Equipment inlet");
            outletPortId = required(outletPortId, "Equipment outlet");
            if (inletPortId.equals(outletPortId)) throw invalid("Equipment inlet/outlet must differ.");
        }
    }

    public record Edge(String id, String fromPortId, String toPortId) {
        public Edge {
            id = required(id, "Edge identity");
            fromPortId = required(fromPortId, "Edge source");
            toPortId = required(toPortId, "Edge destination");
            if (fromPortId.equals(toPortId)) throw invalid("Station edge cannot self-connect.");
        }
    }

    public StationInternalGraph {
        facilityId = required(facilityId, "Station identity");
        revisionId = required(revisionId, "Declared revision identity");
        if (status == null) throw invalid("Explicit graph status required.");
        headers = copied(headers, "Headers");
        units = copied(units, "Units");
        edges = copied(edges, "Edges");
        var headerIds = new HashSet<String>();
        var unitIds = new HashSet<String>();
        var edgeIds = new HashSet<String>();
        var portIds = new HashSet<String>();
        for (var h : headers) {
            if (!headerIds.add(h.id()) || !portIds.add(h.portId())) throw invalid("Duplicate header or port identity.");
        }
        for (var u : units) {
            if (!facilityId.equals(u.facilityId())) throw invalid("Equipment belongs to a different station.");
            if (!unitIds.add(u.equipmentId()) || !portIds.add(u.inletPortId())
                    || !portIds.add(u.outletPortId())) throw invalid("Duplicate installed unit or port identity.");
        }
        for (var e : edges) {
            if (!edgeIds.add(e.id())) throw invalid("Duplicate edge identity.");
        }
    }

    public Set<String> portIds() {
        var ids = new HashSet<String>();
        headers.forEach(h -> ids.add(h.portId()));
        units.forEach(u -> { ids.add(u.inletPortId()); ids.add(u.outletPortId()); });
        return Set.copyOf(ids);
    }

    private static <T> List<T> copied(List<T> values, String name) {
        if (values == null || values.stream().anyMatch(Objects::isNull)) throw invalid(name + " must have no null entries.");
        return List.copyOf(values);
    }
    private static String required(String value, String name) {
        if (value == null || value.isBlank()) throw invalid(name + " required.");
        return value.trim();
    }
    private static InvalidTopologyValueException invalid(String message) {
        return new InvalidTopologyValueException(message);
    }
}
