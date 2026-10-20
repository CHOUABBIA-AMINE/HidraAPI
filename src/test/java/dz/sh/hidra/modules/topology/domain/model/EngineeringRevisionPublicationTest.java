/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Exercises immutable snapshot references and monotonic publication ledger proposals.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.*;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.*;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EngineeringRevisionPublicationTest {
    private static final Scope SCOPE = new Scope(UUID.fromString("00000000-0000-0000-0000-000000000001"), "company", "network");
    private static final UUID DRAFT = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final Instant TIME = Instant.parse("2026-10-20T00:00:00.123456Z");
    private static final String DIGEST = "0".repeat(64);
    private static Component component(long version) { return Component.of(version, "SOURCE_V1", new byte[]{1,2,3}); }
    private static Snapshot empty() {
        return new Snapshot(SCOPE, DRAFT, 1, component(1), null, List.of(),
                List.of("GRAPH_NOT_AUTHORED", EngineeringRevisionPublication.UNQUALIFIED));
    }
    private static Event publish() {
        return EngineeringRevisionPublication.transition(SCOPE, null, 0, null, Action.PUBLISH,
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), DIGEST, TIME);
    }

    @Test void rejectsInvalidScopeVersionEpochAndIdentity() {
        assertThrows(IllegalArgumentException.class, () -> new Scope(SCOPE.contextId(), " company", "network"));
        assertThrows(IllegalArgumentException.class, () -> new Scope(SCOPE.contextId(), "company", "x".repeat(81)));
        assertThrows(IllegalArgumentException.class, () -> component(0));
        assertThrows(IllegalArgumentException.class, () -> new Snapshot(SCOPE, DRAFT, 2, component(1), null,
                List.of(), List.of("GRAPH_NOT_AUTHORED", EngineeringRevisionPublication.UNQUALIFIED)));
        assertThrows(IllegalArgumentException.class, () -> new Head(SCOPE, DRAFT, 0, State.ACTIVE, DRAFT, TIME));
        assertThrows(IllegalArgumentException.class, () -> new Head(SCOPE, DRAFT, 1, State.ACTIVE, DRAFT, TIME.plusNanos(1)));
        assertThrows(IllegalArgumentException.class, () -> new Command(Kind.PUBLISH, "company", "network",
                DRAFT, UUID.randomUUID(), 1L, 0L, DRAFT, null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> new Command(Kind.LIST_HISTORY, "company", "network",
                DRAFT, null, null, null, null, null, null, 101, null));
    }

    @Test void preservesIncompleteEngineeringDiagnostics() {
        var diagnostics = new ArrayList<>(List.of("GRAPH_NOT_AUTHORED", EngineeringRevisionPublication.UNQUALIFIED));
        var snapshot = new Snapshot(SCOPE, DRAFT, 1, component(1), null, List.of(), diagnostics);
        diagnostics.clear();
        assertEquals(List.of("GRAPH_NOT_AUTHORED", EngineeringRevisionPublication.UNQUALIFIED), snapshot.diagnostics());
        assertNull(snapshot.graph());
        assertTrue(snapshot.surveys().isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> snapshot.diagnostics().clear());
        var absent = new Survey("pipe", null, null, null, List.of("SURVEY_GRAPH_STALE"));
        assertNull(absent.source());
        assertEquals(List.of("SURVEY_GRAPH_STALE"), absent.diagnostics());
    }

    @Test void neverInfersPhysicalOrOperationalQualification() {
        var publication = new EngineeringRevisionPublication(UUID.randomUUID(), empty());
        assertFalse(publication.engineeringQualified());
        assertFalse(publication.snapshot().engineeringQualified());
        assertThrows(IllegalArgumentException.class, () -> new Snapshot(SCOPE, DRAFT, 1, component(1), null,
                List.of(), List.of("GRAPH_NOT_AUTHORED")));
        assertThrows(IllegalArgumentException.class, () -> new Survey("pipe", null, null, null, List.of()));
    }

    @Test void rejectsMismatchedComponentReferences() {
        var graph = component(2); var source = component(3);
        var survey = new Survey("pipe", source, 1L, graph.sha256(), List.of());
        assertThrows(IllegalArgumentException.class, () -> new Snapshot(SCOPE, DRAFT, 3, component(3), graph,
                List.of(survey), List.of(EngineeringRevisionPublication.UNQUALIFIED)));
        assertThrows(IllegalArgumentException.class, () -> new Snapshot(SCOPE, DRAFT, 1, component(1), graph,
                List.of(), List.of(EngineeringRevisionPublication.UNQUALIFIED)));
        assertThrows(IllegalArgumentException.class, () -> new Survey("pipe", source, 3L, graph.sha256(), List.of()));
        assertThrows(IllegalArgumentException.class, () -> new Component(1, "SOURCE_V1", DIGEST, "AQID"));
        assertThrows(IllegalArgumentException.class, () -> new Component(1, "SOURCE_V1",
                EngineeringRevisionPublication.hash(new byte[]{1}), "AQ"));
        var valid = new Survey("pipe", source, 2L, graph.sha256(), List.of());
        assertThrows(IllegalArgumentException.class, () -> new Snapshot(SCOPE, DRAFT, 3, component(3), graph,
                List.of(valid, valid), List.of(EngineeringRevisionPublication.UNQUALIFIED)));
    }

    @Test void preservesHeadTransitionsAndEpochMonotonicity() {
        Event first = publish();
        Event withdrawn = EngineeringRevisionPublication.transition(SCOPE, first.head(), 1, first.revisionId(),
                Action.WITHDRAW, first.revisionId(), UUID.randomUUID(), UUID.randomUUID(), DIGEST, TIME);
        Event active = EngineeringRevisionPublication.transition(SCOPE, withdrawn.head(), 2, first.revisionId(),
                Action.REACTIVATE, first.revisionId(), UUID.randomUUID(), UUID.randomUUID(), DIGEST, TIME);
        assertEquals(3, active.epoch()); assertEquals(State.ACTIVE, active.state());
        assertEquals(State.WITHDRAWN, withdrawn.state()); assertEquals(first.revisionId(), active.revisionId());
        assertEquals(TIME, active.effectiveAt());
        assertThrows(IllegalArgumentException.class, () -> EngineeringRevisionPublication.transition(SCOPE, active.head(),
                2, active.revisionId(), Action.PUBLISH, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), DIGEST, TIME));
        assertThrows(IllegalArgumentException.class, () -> EngineeringRevisionPublication.transition(SCOPE, active.head(),
                3, active.revisionId(), Action.REACTIVATE, active.revisionId(), UUID.randomUUID(), UUID.randomUUID(), DIGEST, TIME));
        assertThrows(IllegalArgumentException.class, () -> EngineeringRevisionPublication.transition(SCOPE, active.head(),
                3, active.revisionId(), Action.WITHDRAW, active.revisionId(), UUID.randomUUID(), UUID.randomUUID(), DIGEST, TIME.minusNanos(1000)));
    }

    @Test void rejectsConflictingReplayAndOverflow() {
        Event first = publish();
        var receipt = new Receipt(SCOPE, "actor", first.requestId(), DIGEST, first);
        assertSame(first, receipt.replay(SCOPE, "actor", first.requestId(), DIGEST));
        assertThrows(IllegalArgumentException.class, () -> receipt.replay(SCOPE, "other", first.requestId(), DIGEST));
        assertThrows(IllegalArgumentException.class, () -> receipt.replay(SCOPE, "actor", first.requestId(), "1".repeat(64)));
        assertThrows(IllegalArgumentException.class, () -> receipt.replay(new Scope(UUID.randomUUID(), "company", "network"),
                "actor", first.requestId(), DIGEST));
        var full = new Head(SCOPE, first.revisionId(), Long.MAX_VALUE, State.ACTIVE, first.eventId(), TIME);
        assertThrows(IllegalArgumentException.class, () -> EngineeringRevisionPublication.transition(SCOPE, full,
                Long.MAX_VALUE, full.revisionId(), Action.WITHDRAW, full.revisionId(),
                UUID.randomUUID(), UUID.randomUUID(), DIGEST, TIME));
    }
}
