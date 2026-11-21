/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedPressureRestrictionResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Fail-closed exact-scope and bitemporal pressure restriction resolution.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A deterministic, SQL-free structural resolver. It cannot verify Workflow,
 * Audit, Identity or T07 permission grants: those are an application prerequisite.
 */
public final class QualifiedPressureRestrictionResolver {
    private static final int MAX_ROWS = 4096;
    public enum Status { QUALIFIED, UNAVAILABLE, CONFLICT }
    public record Resolution(Status status, Revision restriction) {
        public Resolution {
            Objects.requireNonNull(status, "status");
            if ((status == Status.QUALIFIED) != (restriction != null))
                throw new IllegalArgumentException("Only a qualified result may include a restriction");
        }
    }

    public Resolution resolve(Scope requested, Instant effectiveAt, Instant recordedBy,
                              List<Revision> revisions, List<Termination> events) {
        Objects.requireNonNull(requested, "requested");
        Objects.requireNonNull(effectiveAt, "effectiveAt");
        Objects.requireNonNull(recordedBy, "recordedBy");
        Objects.requireNonNull(revisions, "revisions");
        Objects.requireNonNull(events, "events");
        if (revisions.size() > MAX_ROWS || events.size() > MAX_ROWS)
            return unavailable();

        Map<UUID, Revision> visible = new HashMap<>();
        for (Revision row : revisions) {
            if (row == null) return unavailable();
            if (!row.scope().equals(requested) || row.recordedAt().isAfter(recordedBy)) continue;
            if (visible.putIfAbsent(row.id(), row) != null) return conflict();
        }

        Map<UUID, Termination> retired = new HashMap<>();
        Set<UUID> eventIds = new HashSet<>();
        for (Termination event : events) {
            if (event == null) return unavailable();
            if (!event.scope().equals(requested) || event.recordedAt().isAfter(recordedBy)) continue;
            if (!eventIds.add(event.id())) return conflict();
            Revision previous = visible.get(event.retiredRevisionId());
            // Never guess what an absent predecessor or a hidden decision meant.
            if (previous == null || event.effectiveAt().isBefore(previous.effectiveFrom()))
                return unavailable();
            if (event.kind() == EventKind.SUPERSEDE) {
                Revision successor = visible.get(event.successorRevisionId());
                if (successor == null || !event.retiredRevisionId().equals(successor.supersedes())
                        || !successor.effectiveFrom().equals(event.effectiveAt()))
                    return unavailable();
            }
            if (retired.putIfAbsent(event.retiredRevisionId(), event) != null) return conflict();
        }

        Revision selected = null;
        for (Revision revision : visible.values()) {
            if (!revision.applies(effectiveAt)) continue;
            if (revision.supersedes() != null) {
                Termination parent = retired.get(revision.supersedes());
                if (parent == null || parent.kind() != EventKind.SUPERSEDE
                        || !revision.id().equals(parent.successorRevisionId()))
                    return unavailable();
            }
            Termination termination = retired.get(revision.id());
            if (termination != null && !effectiveAt.isBefore(termination.effectiveAt())) continue;
            if (selected != null) return conflict();
            selected = revision;
        }
        return selected == null ? unavailable() : new Resolution(Status.QUALIFIED, selected);
    }

    private static Resolution unavailable() { return new Resolution(Status.UNAVAILABLE, null); }
    private static Resolution conflict() { return new Resolution(Status.CONFLICT, null); }
}
