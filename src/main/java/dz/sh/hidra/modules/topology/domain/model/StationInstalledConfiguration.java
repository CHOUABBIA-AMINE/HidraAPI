/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Immutable installed station declaration, separate from physical ownership and approval.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.service.StationInternalGraphValidator;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Pinned declaration, NOT an operating state, qualified manufacturer characteristic,
 * fresh authorization decision or authority to modify an installed unit.
 * Package B independently validates source/current ownership and writes atomically.
 */
public record StationInstalledConfiguration(
        String companyPartyId, String facilityId, String revisionId, String requestId,
        long expectedEpoch, long selectedEpoch, long graphHeadEpoch,
        StationInternalGraphRevision pinnedGraph, String graphPayloadFormat, String graphSha256,
        Instant effectiveFrom, Instant effectiveUntil, Instant recordedAt,
        String sourceReference, String actorBindingId, String actorSessionId, String actorUserId,
        List<Position> positions, List<String> activeEdgeIds) {
    public static final int MAX_POSITIONS = 128;
    public static final int MAX_TEXT_BYTES = 65_536;
    public enum Disposition { CONNECTED, ISOLATED, UNOCCUPIED }

    /** Event witness is a retained source snapshot; it does not itself confer authority. */
    public record Position(String positionId, Disposition disposition, String installedUnitId,
            String installationId, long installationRevision, Instant installationFrom,
            Instant installationUntil, String installationEvidenceReference) {
        public Position {
            positionId = id(positionId, "Position");
            if (disposition == null) throw invalid("Explicit position disposition required.");
            if (disposition == Disposition.UNOCCUPIED) {
                if (installedUnitId != null || installationId != null || installationRevision != 0
                        || installationFrom != null || installationUntil != null
                        || installationEvidenceReference != null)
                    throw invalid("Unoccupied positions cannot invent a unit or installation event.");
            } else {
                installedUnitId = id(installedUnitId, "Physical unit");
                installationId = id(installationId, "Installation");
                if (installationRevision < 1 || installationFrom == null)
                    throw invalid("Occupied position requires an immutable installation witness.");
                installationEvidenceReference = evidence(installationEvidenceReference, "Installation source");
                micro(installationFrom);
                if (installationUntil != null) {
                    micro(installationUntil);
                    if (!installationUntil.isAfter(installationFrom))
                        throw invalid("Installation end must follow its start.");
                }
            }
        }
    }

    public StationInstalledConfiguration {
        companyPartyId = id(companyPartyId, "Company");
        facilityId = id(facilityId, "Facility");
        revisionId = id(revisionId, "Configuration revision");
        requestId = id(requestId, "Request");
        if (expectedEpoch < 0 || expectedEpoch == Long.MAX_VALUE
                || selectedEpoch != expectedEpoch + 1 || graphHeadEpoch < 1)
            throw invalid("Invalid selected epoch or graph publication epoch.");
        if (pinnedGraph == null || pinnedGraph.graph() == null
                || pinnedGraph.qualification() != StationInternalGraphRevision.Qualification.STRUCTURALLY_QUALIFIED
                || pinnedGraph.graph().status() != StationInternalGraph.Status.COMPLETE
                || !facilityId.equals(pinnedGraph.facilityId()))
            throw invalid("Exact, structurally qualified station graph required.");
        if (!"HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1".equals(graphPayloadFormat)
                || graphSha256 == null || !graphSha256.matches("[0-9a-f]{64}"))
            throw invalid("Pinned graph format and canonical SHA-256 required.");
        if (effectiveFrom == null || recordedAt == null) throw invalid("Explicit instants required.");
        micro(effectiveFrom); micro(recordedAt);
        if (effectiveUntil != null) {
            micro(effectiveUntil);
            if (!effectiveUntil.isAfter(effectiveFrom)) throw invalid("Invalid configuration interval.");
        }
        if (pinnedGraph.effectiveFrom().isAfter(effectiveFrom)
                || (pinnedGraph.effectiveUntil() != null
                    && (effectiveUntil == null || pinnedGraph.effectiveUntil().isBefore(effectiveUntil))))
            throw invalid("Graph declaration does not cover the configuration interval.");
        sourceReference = evidence(sourceReference, "Configuration source");
        actorBindingId = id(actorBindingId, "Actor binding");
        actorSessionId = id(actorSessionId, "Actor session");
        actorUserId = id(actorUserId, "Actor user");
        if (positions == null || positions.size() > MAX_POSITIONS || positions.stream().anyMatch(Objects::isNull))
            throw invalid("Positions must be bounded and non-null.");
        if (activeEdgeIds == null || activeEdgeIds.stream().anyMatch(Objects::isNull))
            throw invalid("Active edges must be non-null.");
        var knownPositions = pinnedGraph.graph().units().stream()
                .map(StationInternalGraph.Unit::equipmentId).collect(Collectors.toSet());
        if (knownPositions.size() != positions.size()) throw invalid("Every graph position needs one disposition.");
        var positionIds = new HashSet<String>();
        var unitIds = new HashSet<String>();
        for (var position : positions) {
            if (!knownPositions.contains(position.positionId()) || !positionIds.add(position.positionId()))
                throw invalid("Duplicate, missing or unrelated station position.");
            if (position.disposition() != Disposition.UNOCCUPIED) {
                if (!unitIds.add(position.installedUnitId())) throw invalid("Physical unit reused by multiple positions.");
                if (position.installationFrom().isAfter(effectiveFrom)
                        || (position.installationUntil() != null
                            && (effectiveUntil == null || position.installationUntil().isBefore(effectiveUntil))))
                    throw invalid("Installation witness does not cover the declared range.");
            }
        }
        var edgeIds = new HashSet<String>();
        for (String edge : activeEdgeIds) {
            String value = id(edge, "Active edge");
            if (!edgeIds.add(value)) throw invalid("Duplicate active edge.");
        }
        var graphEdges = pinnedGraph.graph().edges();
        if (!graphEdges.stream().map(StationInternalGraph.Edge::id).collect(Collectors.toSet()).containsAll(edgeIds))
            throw invalid("Active edge not in pinned graph.");
        var connected = positions.stream().filter(p -> p.disposition() == Disposition.CONNECTED)
                .map(Position::positionId).collect(Collectors.toSet());
        var units = pinnedGraph.graph().units().stream().filter(u -> connected.contains(u.equipmentId())).toList();
        var validPorts = new HashSet<String>();
        pinnedGraph.graph().headers().forEach(h -> validPorts.add(h.portId()));
        units.forEach(u -> { validPorts.add(u.inletPortId()); validPorts.add(u.outletPortId()); });
        var edges = graphEdges.stream().filter(e -> edgeIds.contains(e.id())).toList();
        for (var edge : edges)
            if (!validPorts.contains(edge.fromPortId()) || !validPorts.contains(edge.toPortId()))
                throw invalid("Selected edge touches an isolated or unoccupied position.");
        var active = new StationInternalGraph(facilityId, pinnedGraph.revisionId(),
                StationInternalGraph.Status.DRAFT, pinnedGraph.graph().headers(), units, edges);
        new StationInternalGraphValidator().inspect(active);
        positions = positions.stream().sorted(Comparator.comparing(Position::positionId)).toList();
        activeEdgeIds = edgeIds.stream().sorted().toList();
    }

    /** This structural report never asserts flow, valve state or machinery readiness. */
    public StationInternalGraphValidator.Report activeStructure() {
        var connected = positions.stream().filter(p -> p.disposition() == Disposition.CONNECTED)
                .map(Position::positionId).collect(Collectors.toSet());
        var units = pinnedGraph.graph().units().stream().filter(u -> connected.contains(u.equipmentId())).toList();
        var edges = pinnedGraph.graph().edges().stream().filter(e -> activeEdgeIds.contains(e.id())).toList();
        return new StationInternalGraphValidator().inspect(new StationInternalGraph(facilityId,
                pinnedGraph.revisionId(), StationInternalGraph.Status.DRAFT,
                pinnedGraph.graph().headers(), units, edges));
    }

    private static void micro(Instant instant) {
        if (instant.getNano() % 1_000 != 0) throw invalid("Instant must have PostgreSQL microsecond precision.");
    }
    private static String id(String value, String label) {
        if (value == null || value.isBlank() || !value.equals(value.trim())
                || value.codePointCount(0, value.length()) > 80)
            throw invalid(label + " must be an exact bounded identity.");
        return value;
    }
    private static String evidence(String value, String label) {
        if (value == null || value.isBlank() || !value.equals(value.trim())
                || value.getBytes(StandardCharsets.UTF_8).length > MAX_TEXT_BYTES)
            throw invalid(label + " required within evidence limit.");
        return value;
    }
    private static InvalidTopologyValueException invalid(String message) {
        return new InvalidTopologyValueException(message);
    }
}
