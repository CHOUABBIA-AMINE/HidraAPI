/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcScopedReportRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportOwnerPort.Approval;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportOwnerPort.Draft;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Phase;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Scope;
import dz.sh.hidra.modules.reporting.domain.service.AbsoluteDeviationReportJsonV1;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcScopedReportRepositoryAdapter implements ScopedReportRepositoryPort {
    private final JdbcTemplate jdbc;
    private static final String RECEIPT = "public.hidra_reporting_t25_run_receipt";
    public JdbcScopedReportRepositoryAdapter(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Override @Transactional(propagation = Propagation.MANDATORY)
    public void lockAdmission(Scope scope, String key) {
        jdbc.queryForObject("SELECT pg_advisory_xact_lock(hashtextextended(?,0))", Object.class,
                "T25:" + scope.companyId() + ":" + scope.facilityId() + ":" + key);
    }
    @Override
    public Optional<Row> submission(Scope scope, String key) { return scoped("request_key", scope, key, false); }
    @Override
    public Optional<Row> request(Scope scope, String id, boolean lock) { return scoped("request_id", scope, id, lock); }
    @Override
    public Optional<Row> run(Scope scope, String id, boolean lock) { return scoped("run_id", scope, id, lock); }
    @Override
    public Optional<Row> workflowTarget(String requestId) {
        return jdbc.query("SELECT * FROM " + RECEIPT + " WHERE request_id=?",
                (r, n) -> row(r), requestId).stream().findFirst();
    }
    private Optional<Row> scoped(String field, Scope scope, String key, boolean lock) {
        return jdbc.query("SELECT * FROM " + RECEIPT + " WHERE company_id=? AND facility_id=? AND "
                + field + "=?" + (lock ? " FOR UPDATE" : ""), (r,n) -> row(r),
                scope.companyId(), scope.facilityId(), key).stream().findFirst();
    }
    private static Row row(ResultSet r) throws SQLException {
        String document = r.getString("document_id");
        return new Row(new Scope(r.getString("company_id"), r.getString("facility_id")),
                r.getString("request_id"), r.getString("request_key"), r.getString("requester_id"),
                r.getString("definition_id"), r.getString("template_version_id"), r.getString("template_hash"),
                r.getString("metric_run_id"), r.getString("source_hash"), r.getString("run_id"),
                r.getString("render_key"), r.getString("workflow_instance_id"), r.getString("workflow_action_id"),
                r.getString("reviewer_id"), Phase.valueOf(r.getString("phase")),
                document == null ? null : new Draft(document, r.getString("document_version_id"),
                        r.getString("output_hash"), r.getLong("content_length")),
                r.getString("seal_key"), r.getString("custodian_id"), r.getString("acceptance_id"),
                r.getString("failure_code"));
    }
    @Override @Transactional(propagation = Propagation.MANDATORY)
    public Template template(String definition, String version) {
        var templates = jdbc.query("""
                SELECT p.id, v.checksum FROM public.hidra_reporting_report_definition d
                JOIN public.hidra_reporting_report_template_version v ON v.id=d.current_template_version_id
                JOIN public.hidra_reporting_report_template t ON t.id=v.report_template_id
                JOIN public.hidra_reporting_parameter_definition p ON p.report_definition_id=d.id
                WHERE d.id=? AND v.id=? AND d.code=? AND d.status='ACTIVE' AND d.requires_approval
                  AND t.report_definition_id=d.id AND t.active AND v.status='ACTIVE'
                  AND t.template_engine=? AND v.version_number=1 AND v.layout_content_reference=?
                  AND p.code='metricRunId' AND p.parameter_type='REFERENCE' AND p.required AND p.active
                  AND NOT EXISTS (SELECT 1 FROM public.hidra_reporting_parameter_definition other
                       WHERE other.report_definition_id=d.id AND other.active AND other.required AND other.id<>p.id)
                FOR SHARE OF d,v,t,p
                """, (r,n) -> new Template(r.getString(1),r.getString(2)), definition,version,
                AbsoluteDeviationReportJsonV1.PROFILE, AbsoluteDeviationReportJsonV1.PROFILE,
                AbsoluteDeviationReportJsonV1.PROFILE);
        if (templates.size()!=1 || !templates.getFirst().hash().matches("[a-f0-9]{64}"))
            throw new IllegalArgumentException("Invalid report template.");
        return templates.getFirst();
    }
    @Override @Transactional(propagation = Propagation.MANDATORY)
    public void submit(Row row, String parameter, String projection) {
        jdbc.update("""
                INSERT INTO public.hidra_reporting_request
                  (id,report_definition_id,requested_by_actor_id,requested_at,status,created_at,updated_at)
                VALUES (?,?,?,now(),'SUBMITTED',now(),now())
                """, row.requestId(),row.definitionId(),row.actor());
        jdbc.update("""
                INSERT INTO public.hidra_reporting_parameter_value
                  (id,report_request_id,parameter_definition_id,parameter_code,value_type,value_text,created_at)
                VALUES (?,?,?,'metricRunId','REFERENCE',?,now())
                """, UUID.randomUUID().toString(),row.requestId(),parameter,row.metricRunId());
        jdbc.update("""
                INSERT INTO public.hidra_reporting_t25_run_receipt
                  (request_id,company_id,facility_id,request_key,requester_id,definition_id,
                   template_version_id,template_hash,metric_run_id,source_hash,source_projection,phase)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,'SUBMITTED')
                """, row.requestId(),row.scope().companyId(),row.scope().facilityId(),row.requestKey(),
                row.actor(),row.definitionId(),row.templateVersionId(),row.templateHash(),row.metricRunId(),
                row.sourceHash(),projection);
    }
    @Override @Transactional(propagation = Propagation.MANDATORY)
    public void queue(Row row, String runId, String renderKey, Approval approval) {
        requireOne(jdbc.update("""
                UPDATE public.hidra_reporting_request SET status='APPROVED',workflow_reference_id=?,updated_at=now()
                WHERE id=? AND status='SUBMITTED'
                """, approval.instanceId(),row.requestId()));
        jdbc.update("""
                INSERT INTO public.hidra_reporting_run
                  (id,report_request_id,report_definition_id,template_version_id,status,run_mode,queued_at,created_at,updated_at)
                VALUES (?,?,?,?,'QUEUED','MANUAL',now(),now(),now())
                """, runId,row.requestId(),row.definitionId(),row.templateVersionId());
        requireOne(jdbc.update("""
                UPDATE public.hidra_reporting_t25_run_receipt SET run_id=?,render_key=?,workflow_instance_id=?,
                   workflow_action_id=?,reviewer_id=?,reviewed_at=?,workflow_definition_id=?,
                   workflow_definition_version=?,phase='QUEUED'
                WHERE request_id=? AND phase='SUBMITTED'
                """, runId,renderKey,approval.instanceId(),approval.actionId(),approval.reviewerId(),
                Timestamp.from(approval.actedAt()),approval.workflowDefinitionId(),
                approval.workflowDefinitionVersion(),row.requestId()));
    }
    @Override @Transactional(propagation = Propagation.MANDATORY)
    public void start(String runId) {
        requireOne(jdbc.update("""
                UPDATE public.hidra_reporting_run SET status='RUNNING',started_at=now(),updated_at=now()
                WHERE id=? AND status='QUEUED'
                """, runId));
        requireOne(jdbc.update("UPDATE " + RECEIPT + " SET phase='RUNNING' WHERE run_id=? AND phase='QUEUED'", runId));
    }
    @Override @Transactional(propagation = Propagation.MANDATORY)
    public void rendered(String runId, Draft draft) {
        requireOne(jdbc.update("UPDATE " + RECEIPT + " SET phase='RENDERED',document_id=?,document_version_id=?,"
                + "content_length=?,output_hash=? WHERE run_id=? AND phase='RUNNING'",
                draft.documentId(),draft.versionId(),draft.length(),draft.checksum(),runId));
    }
    @Override @Transactional(propagation = Propagation.MANDATORY)
    public void failed(String runId, String failureCode) {
        requireOne(jdbc.update("""
                UPDATE public.hidra_reporting_run SET status='FAILED', failed_at=now(), failure_reason=?, updated_at=now()
                WHERE id=? AND status='RUNNING'
                """, failureCode,runId));
        requireOne(jdbc.update("UPDATE " + RECEIPT + " SET phase='FAILED',failure_code=? WHERE run_id=? AND phase='RUNNING'",
                failureCode,runId));
    }
    @Override @Transactional(propagation = Propagation.MANDATORY)
    public void complete(Row row, String artifact, String key, String custodian, String acceptance) {
        Draft draft = row.draft();
        jdbc.update("""
                INSERT INTO public.hidra_reporting_output_artifact
                  (id,report_run_id,artifact_type,format,file_name,mime_type,document_reference_id,
                   checksum,size_bytes,generated_at,created_at)
                VALUES (?,?,'PRIMARY_REPORT','JSON','absolute-deviation.json','application/json',?,?,?,?,now())
                """, artifact,row.runId(),draft.documentId(),draft.checksum(),draft.length(),
                Timestamp.from(java.time.Instant.now()));
        requireOne(jdbc.update("""
                UPDATE public.hidra_reporting_run SET status='COMPLETED',completed_at=now(),updated_at=now(),
                   record_count=1,output_count=1 WHERE id=? AND status='RUNNING'
                """, row.runId()));
        requireOne(jdbc.update("UPDATE " + RECEIPT + " SET phase='COMPLETED',seal_key=?,custodian_id=?,acceptance_id=?,"
                + "artifact_id=? WHERE run_id=? AND phase='RENDERED'", key,custodian,acceptance,artifact,row.runId()));
    }
    @Override
    public boolean managedRequest(String id) {
        return Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS (SELECT 1 FROM " + RECEIPT
                + " WHERE request_id=?)",Boolean.class,id));
    }
    @Override
    public boolean managedRun(String id) {
        return Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS (SELECT 1 FROM " + RECEIPT
                + " WHERE run_id=?)",Boolean.class,id));
    }
    private static void requireOne(int count) {
        if (count!=1) throw new IllegalStateException("Report state conflict.");
    }
}
