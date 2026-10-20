/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublication
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Pins immutable authored source bytes and context ledger transitions without engineering qualification.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

/** Governed software evidence; neither a physical revision nor operational approval. */
public record EngineeringRevisionPublication(UUID revisionId, Snapshot snapshot) {
    public static final int MAX_BYTES = 32 * 1024 * 1024;
    public static final String UNQUALIFIED = "PHYSICAL_QUALIFICATION_NOT_ESTABLISHED";
    public EngineeringRevisionPublication {
        Objects.requireNonNull(revisionId);
        Objects.requireNonNull(snapshot);
    }
    public boolean engineeringQualified() { return false; }

    public record Scope(UUID contextId, String companyPartyId, String pipelineSystemId) {
        public Scope {
            Objects.requireNonNull(contextId);
            companyPartyId = scopeIdentity(companyPartyId);
            pipelineSystemId = scopeIdentity(pipelineSystemId);
        }
    }

    /** Original canonical source encoding, not a decoded or reconstructed DTO. */
    public record Component(long version, String format, String sha256, String base64) {
        public Component {
            if (version < 1 || format == null || format.length() > 80
                    || !format.matches("[A-Z0-9_]+")) throw invalid();
            digest(sha256);
            if (base64 == null || base64.length() > 4 * ((16 * 1024 * 1024 + 2) / 3)) throw invalid();
            byte[] bytes = Base64.getDecoder().decode(base64);
            if (!Base64.getEncoder().encodeToString(bytes).equals(base64)
                    || !hash(bytes).equals(sha256)) throw invalid();
        }
        public static Component of(long version, String format, byte[] bytes) {
            Objects.requireNonNull(bytes);
            return new Component(version, format, hash(bytes), Base64.getEncoder().encodeToString(bytes));
        }
    }

    /** Absence and stale binding remain explicit; stale source bytes cannot masquerade as selected survey. */
    public record Survey(String branchId, Component source, Long graphVersion, String graphSha256,
            List<String> diagnostics) {
        public Survey {
            branchId = EngineeringGraphDraft.identity(branchId);
            diagnostics = findings(diagnostics);
            if (source == null) {
                if (graphVersion != null || graphSha256 != null
                        || !diagnostics.contains("SURVEY_NOT_AUTHORED") && !diagnostics.contains("SURVEY_GRAPH_STALE"))
                    throw invalid();
            } else {
                if (graphVersion == null || graphVersion < 1 || graphVersion >= source.version()) throw invalid();
                digest(graphSha256);
                if (diagnostics.contains("SURVEY_NOT_AUTHORED") || diagnostics.contains("SURVEY_GRAPH_STALE")) throw invalid();
            }
        }
    }

    public record Snapshot(Scope scope, UUID draftId, long version, Component metadata, Component graph,
            List<Survey> surveys, List<String> diagnostics) {
        public Snapshot {
            Objects.requireNonNull(scope);
            Objects.requireNonNull(draftId);
            Objects.requireNonNull(metadata);
            if (version < 1 || metadata.version() != version || graph != null && graph.version() > version)
                throw invalid();
            if (surveys == null || surveys.size() > EngineeringGraphDraft.MAX_BRANCHES) throw invalid();
            surveys = List.copyOf(surveys);
            diagnostics = findings(diagnostics);
            if (!diagnostics.contains(UNQUALIFIED) || graph == null &&
                    (!surveys.isEmpty() || !diagnostics.contains("GRAPH_NOT_AUTHORED"))) throw invalid();
            String previous = null;
            long total = metadata.base64().length() * 3L / 4 + (graph == null ? 0 : graph.base64().length() * 3L / 4);
            for (Survey survey : surveys) {
                if (previous != null && previous.compareTo(survey.branchId()) >= 0) throw invalid();
                previous = survey.branchId();
                if (survey.source() != null) {
                    if (graph == null || survey.source().version() > version
                            || survey.graphVersion() != graph.version()
                            || !survey.graphSha256().equals(graph.sha256())) throw invalid();
                    total += survey.source().base64().length() * 3L / 4;
                }
            }
            if (total > MAX_BYTES) throw invalid();
        }
        public boolean engineeringQualified() { return false; }
    }

    public enum Action { PUBLISH, WITHDRAW, REACTIVATE }
    public enum State { ACTIVE, WITHDRAWN }

    public record Head(Scope scope, UUID revisionId, long epoch, State state, UUID eventId, Instant effectiveAt) {
        public Head {
            Objects.requireNonNull(scope); Objects.requireNonNull(revisionId);
            Objects.requireNonNull(state); Objects.requireNonNull(eventId);
            microseconds(effectiveAt);
            if (epoch < 1) throw invalid();
        }
    }

    public record Event(UUID eventId, Scope scope, long epoch, Action action, UUID priorRevisionId,
            UUID revisionId, State state, UUID requestId, String commandSha256, Instant effectiveAt) {
        public Event {
            Objects.requireNonNull(eventId); Objects.requireNonNull(scope); Objects.requireNonNull(action);
            Objects.requireNonNull(revisionId); Objects.requireNonNull(state); Objects.requireNonNull(requestId);
            digest(commandSha256); microseconds(effectiveAt);
            if (epoch < 1 || (epoch == 1) != (priorRevisionId == null) || (action == Action.WITHDRAW ? state != State.WITHDRAWN : state != State.ACTIVE)
                    || action != Action.PUBLISH && !revisionId.equals(priorRevisionId)
                    || action == Action.PUBLISH && revisionId.equals(priorRevisionId)) throw invalid();
        }
        public Head head() { return new Head(scope, revisionId, epoch, state, eventId, effectiveAt); }
    }

    /** Validates a proposal only. Database admission/CAS and actual COMMIT remain mandatory. */
    public static Event transition(Scope scope, Head current, long expectedEpoch, UUID expectedRevisionId,
            Action action, UUID revisionId, UUID eventId, UUID requestId, String commandSha256, Instant sampledAt) {
        Objects.requireNonNull(action);
        if (expectedEpoch < 0 || (expectedEpoch == 0) != (expectedRevisionId == null)) throw invalid();
        if (current == null) {
            if (expectedEpoch != 0 || action != Action.PUBLISH) throw invalid();
        } else {
            if (!scope.equals(current.scope()) || expectedEpoch != current.epoch()
                    || !current.revisionId().equals(expectedRevisionId) || sampledAt.isBefore(current.effectiveAt()))
                throw invalid();
            if (action == Action.PUBLISH ? current.revisionId().equals(revisionId)
                    : !current.revisionId().equals(revisionId)
                        || action == Action.WITHDRAW && current.state() != State.ACTIVE
                        || action == Action.REACTIVATE && current.state() != State.WITHDRAWN) throw invalid();
        }
        long next;
        try { next = Math.addExact(expectedEpoch, 1); } catch (ArithmeticException overflow) { throw invalid(); }
        return new Event(eventId, scope, next, action, expectedRevisionId, revisionId,
                action == Action.WITHDRAW ? State.WITHDRAWN : State.ACTIVE, requestId, commandSha256, sampledAt);
    }

    /** Scoped actor identity must come from a fresh qualified native admission, never request input. */
    public record Receipt(Scope scope, String actorUserId, UUID requestId, String commandSha256, Event outcome) {
        public Receipt {
            Objects.requireNonNull(scope); actorUserId = EngineeringGraphDraft.identity(actorUserId);
            Objects.requireNonNull(requestId); digest(commandSha256); Objects.requireNonNull(outcome);
            if (!scope.equals(outcome.scope()) || !requestId.equals(outcome.requestId())
                    || !commandSha256.equals(outcome.commandSha256())) throw invalid();
        }
        public Event replay(Scope actualScope, String actualActor, UUID request, String commandDigest) {
            if (!scope.equals(actualScope) || !actorUserId.equals(actualActor)
                    || !requestId.equals(request) || !commandSha256.equals(commandDigest)) throw invalid();
            return outcome;
        }
    }

    /** Untrusted page data, not an authorization capability or a signed token. */
    public record Cursor(Scope scope, Instant asOf, long highWaterEpoch, long beforeEpoch) {
        public Cursor {
            Objects.requireNonNull(scope);
            if (asOf != null) microseconds(asOf);
            if (beforeEpoch < 1 || highWaterEpoch < beforeEpoch) throw invalid();
        }
        public Cursor advance(long lastReturnedEpoch) {
            if (lastReturnedEpoch < 1 || lastReturnedEpoch >= beforeEpoch) throw invalid();
            return new Cursor(scope, asOf, highWaterEpoch, lastReturnedEpoch);
        }
        public boolean includes(long epoch) { return epoch > 0 && epoch <= highWaterEpoch && epoch < beforeEpoch; }
    }

    /** Retains existing scope spelling and character bounds while rejecting malformed UTF-16. */
    public static String scopeIdentity(String value) {
        EngineeringNetworkDraft.identity(value);
        for (int n = 0; n < value.length(); n++) {
            char c = value.charAt(n);
            if (Character.isHighSurrogate(c)) {
                if (n + 1 >= value.length() || !Character.isLowSurrogate(value.charAt(++n))) throw invalid();
            } else if (Character.isLowSurrogate(c)) throw invalid();
        }
        return value;
    }

    public static Instant microseconds(Instant value) {
        if (value == null || value.getNano() % 1000 != 0) throw invalid();
        return value;
    }
    private static List<String> findings(List<String> values) {
        if (values == null || values.size() > 64) throw invalid();
        var copy = List.copyOf(values);
        if (new HashSet<>(copy).size() != copy.size()) throw invalid();
        for (String value : copy) if (!value.matches("[A-Z][A-Z0-9_]{0,79}")) throw invalid();
        return copy;
    }
    private static void digest(String value) {
        if (value == null || !value.matches("[0-9a-f]{64}")) throw invalid();
    }
    public static String hash(byte[] bytes) {
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes)); }
        catch (NoSuchAlgorithmException impossible) { throw new IllegalStateException(impossible); }
    }
    private static IllegalArgumentException invalid() { return new IllegalArgumentException("Invalid publication evidence"); }
}
