/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcPressureRestrictionDecisionAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persists exact pressure restriction requests and reads their publication receipts.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase.Conflict;
import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase.Published;
import dz.sh.hidra.modules.topology.application.port.in.PressureRestrictionDecisionUseCase.Target;
import dz.sh.hidra.modules.topology.application.port.out.PressureRestrictionDecisionEvidencePort.Approval;
import dz.sh.hidra.modules.topology.application.port.out.PressureRestrictionDecisionPort;
import dz.sh.hidra.modules.topology.application.port.out.QualifiedOperatingConstraintReadPort;
import dz.sh.hidra.modules.topology.domain.model.PressureRestrictionDecisionRequest;
import dz.sh.hidra.modules.topology.domain.model.PressureRestrictionDecisionRequest.Source;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Scope;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureWorkflowCandidate;
import jakarta.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.UUID;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPressureRestrictionDecisionAdapter implements PressureRestrictionDecisionPort {
    private static final String REQUEST_COLUMNS =
            "SELECT request_id,expected_version,revision_candidate_id,termination_candidate_id "
            + "FROM public.hidra_topology_pressure_decision_request ";
    private final JdbcTemplate jdbc;
    private final EntityManager entityManager;
    private final QualifiedOperatingConstraintReadPort candidates;

    public JdbcPressureRestrictionDecisionAdapter(JdbcTemplate jdbc, EntityManager entityManager,
                                                 QualifiedOperatingConstraintReadPort candidates) {
        this.jdbc = jdbc;
        this.entityManager = entityManager;
        this.candidates = candidates;
    }

    @Override
    public Optional<StoredRequest> request(Target target) {
        return jdbc.query(REQUEST_COLUMNS
                + "WHERE request_id=? AND company_party_id=? AND pipeline_id=? AND section_id=?",
                this::mapRequest, target.requestId(), target.companyPartyId(), target.pipelineId(),
                target.sectionId() == null ? "" : target.sectionId()).stream().findFirst();
    }

    @Override
    public Optional<StoredRequest> requestByCandidate(UUID candidateId) {
        return jdbc.query(REQUEST_COLUMNS + "WHERE revision_candidate_id=? OR termination_candidate_id=? LIMIT 2",
                this::mapRequest, candidateId, candidateId).stream().reduce((first, second) -> { throw new Conflict(); });
    }

    @Override
    public OptionalLong currentStreamVersion(Scope scope, String systemId) {
        return jdbc.query("""
                SELECT COALESCE((
                    SELECT publication_version FROM public.hidra_topology_pressure_decision_stream
                    WHERE pipeline_system_id=? AND pipeline_id=? AND section_id=?
                      AND company_party_id=? AND condition_code=?
                ), 0)
                WHERE EXISTS (
                    SELECT 1 FROM public.hidra_topology_t36_condition_policy
                    WHERE company_party_id=? AND condition_code=?
                )
                """, rows -> rows.next() ? OptionalLong.of(rows.getLong(1)) : OptionalLong.empty(),
                systemId, scope.pipelineId(), section(scope), scope.companyPartyId(), scope.conditionCode(),
                scope.companyPartyId(), scope.conditionCode());
    }

    @Override
    public long lockStream(Scope scope, String systemId) {
        Long version = jdbc.queryForObject("SELECT public.hidra_topology_t38_lock_stream(?,?,?,?,?)",
                Long.class, scope.companyPartyId(), systemId, scope.pipelineId(), section(scope), scope.conditionCode());
        if (version == null) throw new Conflict();
        return version;
    }

    @Override
    public boolean lockSource(Scope scope, Source source) {
        return Boolean.TRUE.equals(jdbc.queryForObject("SELECT public.hidra_topology_t38_lock_source(?,?,?,?,?,?)",
                Boolean.class, scope.companyPartyId(), source.pipelineSystemId(), scope.pipelineId(), section(scope),
                source.receiptId(), source.manifestDigest()));
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public boolean guard(StoredRequest request, long streamVersion) {
        return Boolean.TRUE.equals(jdbc.queryForObject("SELECT public.hidra_topology_t38_guard(?,?)",
                Boolean.class, request.request().requestId(), streamVersion));
    }

    @Override
    public StoredRequest submit(StoredRequest stored) {
        var request = stored.request();
        var scope = request.scope();
        var source = stored.source();
        var initial = stored.candidates().getFirst();
        boolean hasRevision = initial.kind() == QualifiedPressureWorkflowCandidate.Kind.REVISION;
        UUID revision = hasRevision ? initial.id() : null;
        UUID termination = !hasRevision || stored.candidates().size() == 2 ? stored.candidates().getLast().id() : null;
        try {
            UUID result = jdbc.queryForObject("""
                    SELECT public.hidra_topology_t38_submit(
                     ?,?,?,?,?,?,?,?,?,?,?::numeric,?,?,?::timestamptz,?::timestamptz,?,?,?,?,?::timestamptz)
                    """, UUID.class, request.requestId(), scope.companyPartyId(), source.pipelineSystemId(),
                    scope.pipelineId(), section(scope), scope.conditionCode(), request.expectedVersion(), revision,
                    termination, initial.retiredRevisionId(), initial.upperPressure(), initial.pressureUnit(),
                    initial.pressureBasis() == null ? null : initial.pressureBasis().name(), time(initial.effectiveFrom()),
                    time(initial.effectiveUntil()), source.receiptId(), source.manifestDigest(), source.revisionId(),
                    stored.submitterId(), time(stored.submittedAt()));
            if (!request.requestId().equals(result)) throw new Conflict();
            return stored;
        } catch (DataIntegrityViolationException mismatch) {
            throw new Conflict();
        }
    }

    @Override
    public Optional<StoredPublication> publication(UUID requestId) {
        var matches = jdbc.query("""
                SELECT stream_version,revision_id,termination_id,recorded_at
                FROM public.hidra_topology_pressure_decision_publication WHERE request_id=?
                """, (rs,i) -> new Published(requestId, rs.getLong("stream_version"),
                rs.getObject("revision_id", UUID.class), rs.getObject("termination_id", UUID.class),
                rs.getTimestamp("recorded_at").toInstant()), requestId);
        if (matches.isEmpty()) return Optional.empty();
        var decisions = jdbc.query("""
                SELECT w.candidate_id,w.instance_id,w.task_id,w.action_id,w.audit_event_id,
                       c.submitted_by,coalesce(r.reviewed_by,t.reviewed_by) AS reviewer,
                       coalesce(r.approved_at,t.approved_at) AS approved_at
                FROM public.hidra_topology_pressure_decision_witness w
                JOIN public.hidra_topology_t36_pressure_candidate c ON c.candidate_id=w.candidate_id
                LEFT JOIN public.hidra_topology_t36_pressure_revision r
                    ON r.revision_id=c.candidate_id AND r.decision_action_id=w.action_id AND r.audit_event_id=w.audit_event_id
                LEFT JOIN public.hidra_topology_t36_pressure_termination t
                    ON t.event_id=c.candidate_id AND t.decision_action_id=w.action_id AND t.audit_event_id=w.audit_event_id
                WHERE w.request_id=? AND (r.revision_id IS NOT NULL OR t.event_id IS NOT NULL)
                ORDER BY w.position
                """, (rs,i) -> {
            UUID candidate = rs.getObject("candidate_id", UUID.class);
            return new BoundDecision(new Approval(candidate, "t36-v1:" + candidate, rs.getString("instance_id"),
                    rs.getString("task_id"), rs.getString("action_id"), rs.getString("submitted_by"),
                    rs.getString("reviewer"), rs.getTimestamp("approved_at").toInstant()), rs.getString("audit_event_id"));
        }, requestId);
        return Optional.of(new StoredPublication(matches.getFirst(), decisions));
    }

    @Override
    public StoredPublication publish(StoredRequest request, long version, Instant recordedAt,
                                     List<BoundDecision> decisions) {
        try {
            Long publishedVersion = jdbc.query(connection -> {
                var statement = connection.prepareStatement("""
                        SELECT public.hidra_topology_t38_publish(?,?,?,?,?,?,?,?,?)
                        """);
                statement.setObject(1, request.request().requestId());
                statement.setLong(2, version);
                statement.setTimestamp(3, time(recordedAt));
                statement.setArray(4, connection.createArrayOf("varchar", decisions.stream().map(d -> d.approval().instanceId()).toArray()));
                statement.setArray(5, connection.createArrayOf("varchar", decisions.stream().map(d -> d.approval().taskId()).toArray()));
                statement.setArray(6, connection.createArrayOf("varchar", decisions.stream().map(d -> d.approval().actionId()).toArray()));
                statement.setArray(7, connection.createArrayOf("varchar", decisions.stream().map(BoundDecision::auditEventId).toArray()));
                statement.setArray(8, connection.createArrayOf("varchar", decisions.stream().map(d -> d.approval().reviewerId()).toArray()));
                statement.setArray(9, connection.createArrayOf("timestamptz", decisions.stream()
                        .map(d -> d.approval().actedAt().toString()).toArray(String[]::new)));
                return statement;
            }, results -> results.next() ? results.getLong(1) : null);
            var publication = publication(request.request().requestId()).orElseThrow(Conflict::new);
            if (publishedVersion == null || publication.receipt().streamVersion() != publishedVersion
                    || !publication.decisions().equals(decisions)) throw new Conflict();
            return publication;
        } catch (DataIntegrityViolationException mismatch) {
            throw new Conflict();
        }
    }

    private StoredRequest mapRequest(ResultSet rs, int row) throws SQLException {
        var ordered = new ArrayList<QualifiedPressureWorkflowCandidate>(2);
        for (String column : List.of("revision_candidate_id", "termination_candidate_id")) {
            UUID id = rs.getObject(column, UUID.class);
            if (id != null) ordered.add(candidates.candidate(id).orElseThrow(Conflict::new));
        }
        var first = ordered.getFirst();
        var source = new Source(first.pipelineSystemId(), first.baselineReceiptId(), first.baselineDigest(),
                first.engineeringSourceRevision());
        PressureRestrictionDecisionRequest.Change change;
        if (first.kind() == QualifiedPressureWorkflowCandidate.Kind.WITHDRAW) {
            change = new PressureRestrictionDecisionRequest.Withdrawal(first.retiredRevisionId(), first.effectiveFrom());
        } else if (first.kind() == QualifiedPressureWorkflowCandidate.Kind.REVISION) {
            var pressure = new PressureRestrictionDecisionRequest.Pressure(first.upperPressure(), first.pressureUnit(),
                    first.pressureBasis(), first.effectiveFrom(), first.effectiveUntil());
            change = first.retiredRevisionId() == null ? new PressureRestrictionDecisionRequest.Initial(pressure)
                    : new PressureRestrictionDecisionRequest.Replacement(first.retiredRevisionId(), pressure);
        } else {
            throw new Conflict();
        }
        var command = new PressureRestrictionDecisionRequest(rs.getObject("request_id", UUID.class), first.scope(),
                first.baselineReceiptId(), rs.getLong("expected_version"), change);
        var expected = command.candidates(source, first.submittedBy(), first.createdAt());
        for (int index = 0; index < expected.size(); index++) {
            if (index >= ordered.size() || !expected.get(index).id().equals(ordered.get(index).id())
                    || !source.receiptId().equals(ordered.get(index).baselineReceiptId())
                    || !source.manifestDigest().equals(ordered.get(index).baselineDigest())
                    || !source.revisionId().equals(ordered.get(index).engineeringSourceRevision())
                    || !first.scope().equals(ordered.get(index).scope())
                    || !first.pipelineSystemId().equals(ordered.get(index).pipelineSystemId())
                    || !first.submittedBy().equals(ordered.get(index).submittedBy())) throw new Conflict();
        }
        if (expected.size() != ordered.size()) throw new Conflict();
        return new StoredRequest(command, source, first.submittedBy(), first.createdAt(), ordered);
    }

    private static Timestamp time(Instant value) {
        return value == null ? null : Timestamp.from(value);
    }

    private static String section(Scope scope) {
        return scope.sectionId() == null ? "" : scope.sectionId();
    }
}
