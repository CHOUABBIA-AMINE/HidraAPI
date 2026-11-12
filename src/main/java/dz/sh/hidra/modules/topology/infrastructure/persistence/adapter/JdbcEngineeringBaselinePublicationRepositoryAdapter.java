/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcEngineeringBaselinePublicationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure.Kind;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselinePublicationRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineSourcePort.Original;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselinePublication.*;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcEngineeringBaselinePublicationRepositoryAdapter implements EngineeringBaselinePublicationRepositoryPort {
    private static final String REVISION = "public.hidra_topology_engineering_baseline_revision";
    private static final String SUBMISSION = "public.hidra_topology_baseline_submission";
    private static final String PUBLICATION = "public.hidra_topology_baseline_publication";
    private static final String SUBMIT = "INSERT INTO public.hidra_topology_baseline_submission (company_party_id,pipeline_system_id,submission_id,manifest_id,version,canonical_sha256,submitted_by,submitted_at) VALUES (?,?,?,?,?,?,?,?)";
    private static final String PUBLISH = "INSERT INTO public.hidra_topology_baseline_publication (company_party_id,pipeline_system_id,receipt_id,submission_id,epoch,definition_id,definition_version,target_type_id,purpose_id,instance_id,task_id,action_id,reviewer_id,reviewed_at,published_by,published_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final JdbcTemplate jdbc;
    private final ProtectedTransactionExecutor executor;
    private final EngineeringBaselineManifestCodec codec;

    public JdbcEngineeringBaselinePublicationRepositoryAdapter(JdbcTemplate jdbc, ProtectedTransactionExecutor executor,
            @Value("${hidra.topology.engineering-baseline.max-pins:128}") int maxPins,
            @Value("${hidra.topology.engineering-baseline.max-bytes:1048576}") int maxBytes) {
        this.jdbc = jdbc;
        this.executor = executor;
        this.codec = new EngineeringBaselineManifestCodec(maxPins, maxBytes);
    }

    @Override
    public void lock() {
        executor.requireEngineeringBaselinePublicationStorageLock();
        jdbc.queryForObject("SELECT public.hidra_topology_baseline_lock_storage()", Boolean.class);
        jdbc.queryForObject("SELECT public.hidra_topology_baseline_lock_publication()", Boolean.class);
    }

    @Override
    public boolean scopeAvailable(String company, String pipeline) {
        return Boolean.TRUE.equals(jdbc.queryForObject("""
                SELECT EXISTS (
                    SELECT 1 FROM public.hidra_party_party p
                    JOIN public.hidra_party_eligibility_generation g ON g.party_id=p.id
                    WHERE p.id=? AND p.status='ACTIVE' AND g.present
                ) AND EXISTS (
                    SELECT 1 FROM public.hidra_topology_pipeline_system s
                    JOIN public.hidra_topology_pipeline_system_type t ON t.id=s.system_type_id
                    WHERE s.id=? AND s.status IN ('DRAFT','ACTIVE') AND t.active
                )
                """, Boolean.class, company, pipeline));
    }

    @Override
    public Submission draft(Command c, Original o) {
        var revisions = jdbc.query("SELECT r.* FROM " + REVISION + " r WHERE r.company_party_id=?"
                        + " AND r.pipeline_system_id=? AND r.manifest_id=? AND r.version=?",
                (row, n) -> submission(row, c.id(), o.userId(), Timestamp.from(o.sampledAt())),
                c.company(), c.pipeline(), c.manifestId(), c.version());
        if (revisions.isEmpty()) throw failure(Kind.NOT_FOUND);
        return revisions.getFirst();
    }

    @Override
    public Submission submission(String company, String pipeline, UUID id) {
        var values = jdbc.query("SELECT r.*,s.submitted_by,s.submitted_at FROM " + SUBMISSION
                        + " s JOIN " + REVISION + " r USING (company_party_id,pipeline_system_id,manifest_id,version,canonical_sha256)"
                        + " WHERE s.company_party_id=? AND s.pipeline_system_id=? AND s.submission_id=?",
                (row, n) -> submission(row, id, row.getString("submitted_by"), row.getTimestamp("submitted_at")),
                company, pipeline, id);
        return values.isEmpty() ? null : values.getFirst();
    }

    private Submission submission(ResultSet row, UUID id, String author, Timestamp submittedAt) throws SQLException {
        byte[] bytes = row.getBytes("canonical_payload");
        String sha = row.getString("canonical_sha256");
        if (!EngineeringBaselineManifestCodec.FORMAT.equals(row.getString("payload_format")) || !codec.sha256(bytes).equals(sha))
            throw failure(Kind.UNAVAILABLE);
        var manifest = codec.decode(bytes);
        if (!manifest.companyPartyId().equals(row.getString("company_party_id"))
                || !manifest.pipelineSystemId().equals(row.getString("pipeline_system_id"))
                || !manifest.manifestId().equals(row.getObject("manifest_id", UUID.class))
                || manifest.version() != row.getLong("version"))
            throw failure(Kind.UNAVAILABLE);
        return new Submission(id, manifest, sha, author, submittedAt.toInstant());
    }

    @Override
    public Receipt receipt(String company, String pipeline, UUID id) {
        var values = jdbc.query("SELECT r.*,s.submitted_by,s.submitted_at,p.submission_id,p.epoch,"
                        + "p.definition_id,p.definition_version,p.target_type_id,p.purpose_id,p.instance_id,p.task_id,"
                        + "p.action_id,p.reviewer_id,p.reviewed_at,p.published_by,p.published_at FROM " + PUBLICATION
                        + " p JOIN " + SUBMISSION + " s USING (company_party_id,pipeline_system_id,submission_id)"
                        + " JOIN " + REVISION + " r USING (company_party_id,pipeline_system_id,manifest_id,version,canonical_sha256)"
                        + " WHERE p.company_party_id=? AND p.pipeline_system_id=? AND p.receipt_id=?",
                (row, n) -> {
                    var s = submission(row, row.getObject("submission_id", UUID.class),
                            row.getString("submitted_by"), row.getTimestamp("submitted_at"));
                    var d = new Decision(row.getString("definition_id"), row.getInt("definition_version"),
                            row.getString("target_type_id"), row.getString("purpose_id"), row.getString("instance_id"),
                            row.getString("task_id"), row.getString("action_id"));
                    var a = new Approval(d, row.getString("reviewer_id"), row.getTimestamp("reviewed_at").toInstant());
                    return new Receipt(id, s, row.getLong("epoch"), a, row.getString("published_by"),
                            row.getTimestamp("published_at").toInstant());
                }, company, pipeline, id);
        return values.isEmpty() ? null : values.getFirst();
    }

    @Override
    public long head(String company, String pipeline) {
        return jdbc.queryForObject("SELECT coalesce(max(epoch),0) FROM " + PUBLICATION
                + " WHERE company_party_id=? AND pipeline_system_id=?", Long.class, company, pipeline);
    }

    @Override
    public boolean published(String company, String pipeline, UUID submission) {
        return Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS (SELECT 1 FROM " + PUBLICATION
                + " WHERE company_party_id=? AND pipeline_system_id=? AND submission_id=?)",
                Boolean.class, company, pipeline, submission));
    }

    @Override public Set<String> mutationSql() { return Set.of(SUBMIT, PUBLISH); }

    @Override
    public void insert(Submission s) {
        var m = s.manifest();
        if (jdbc.update(SUBMIT, m.companyPartyId(), m.pipelineSystemId(), s.id(), m.manifestId(), m.version(),
                s.sha256(), s.submittedBy(), Timestamp.from(s.submittedAt())) != 1) throw failure(Kind.CONFLICT);
    }

    @Override
    public void insert(Receipt r) {
        var m = r.submission().manifest();
        var approval = r.approval();
        var d = approval.decision();
        if (jdbc.update(PUBLISH, m.companyPartyId(), m.pipelineSystemId(), r.id(), r.submission().id(), r.epoch(),
                d.definitionId(), d.definitionVersion(), d.targetTypeId(), d.purposeId(), d.instanceId(),
                d.taskId(), d.actionId(), approval.reviewerId(), Timestamp.from(approval.reviewedAt()),
                r.publishedBy(), Timestamp.from(r.publishedAt())) != 1) throw failure(Kind.CONFLICT);
    }

    @Override
    public List<ProtectedRelationFence> fences() {
        return List.of(SUBMISSION, PUBLICATION, REVISION, "public.hidra_party_party",
                        "public.hidra_party_eligibility_generation", "public.hidra_topology_pipeline_system",
                        "public.hidra_topology_pipeline_system_type").stream()
                .map(relation -> new ProtectedRelationFence(relation, ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE))
                .toList();
    }

    @Override
    public boolean guard(Original o, List<ProtectedRelationFence> fences) {
        var database = executor.publicationDatabaseIdentity();
        var sql = new StringBuilder("""
                WITH sampled_clock AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS t)
                SELECT pg_backend_pid()=? AND txid_current()=? AND current_user=? AND session_user=?
                  AND current_user=? AND current_user<>? AND current_setting('session_replication_role')='origin'
                  AND (SELECT t FROM sampled_clock)>=?::timestamptz AND (SELECT t FROM sampled_clock)<?::timestamptz
                """);
        var arguments = new ArrayList<Object>(List.of(o.backendPid(), o.transactionId(), o.databaseRole(),
                o.databaseRole(), database.publisherUser(), database.ordinaryUser(),
                Timestamp.from(o.sampledAt()), Timestamp.from(o.validUntil())));
        for (var fence : fences) {
            sql.append(" AND EXISTS (SELECT 1 FROM pg_locks WHERE pid=pg_backend_pid() AND granted"
                    + " AND locktype='relation' AND relation=?::regclass AND mode=?)");
            arguments.add(fence.relation());
            arguments.add(fence.mode().catalog());
        }
        return Boolean.TRUE.equals(jdbc.queryForObject(sql.toString(), Boolean.class, arguments.toArray()));
    }

    private static EngineeringBaselineDraftFailure failure(Kind kind) {
        return new EngineeringBaselineDraftFailure(kind);
    }
}
