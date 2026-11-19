/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcScopedAuditEvidenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.adapter
 *
 * @Description : PostgreSQL-guarded immutable Audit event-scope binding and frozen exports.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.audit.application.port.out.ScopedAuditEvidenceRepositoryPort;
import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence;
import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** SQL cannot read another business module's table. Owner scope comes from a trusted contract. */
@Component
@Transactional(propagation=Propagation.MANDATORY)
public class JdbcScopedAuditEvidenceRepositoryAdapter implements ScopedAuditEvidenceRepositoryPort {
    private static final String S="public.hidra_audit_t28_event_scope";
    private static final String X="public.hidra_audit_t28_export";
    private static final String A="public.hidra_audit_t28_access_receipt";
    private final JdbcTemplate jdbc;
    public JdbcScopedAuditEvidenceRepositoryAdapter(JdbcTemplate jdbc){this.jdbc=Objects.requireNonNull(jdbc);}
    @Override public void bindApproved(String eventId,String actionId,String assessmentId,
            Scope scope,Instant occurredAt) {
        int rows=jdbc.update("""
            INSERT INTO public.hidra_audit_t28_event_scope
               (event_id,company_id,facility_id,event_type,workflow_action_id,occurred_at)
            SELECT e.id,?,?, 'RISK_ASSESSMENT_APPROVED', ?, ?
            FROM public.hidra_audit_event e
            JOIN public.hidra_audit_catalog_entry cat ON e.event_type_id=cat.id
            WHERE e.id=? AND e.target_id=? AND e.workflow_action_id=?
             AND e.occurred_at=? AND cat.code='RISK_ASSESSMENT_APPROVED'
             AND e.source_module='risk' AND e.target_module='risk'
             AND e.target_type='RISK_ASSESSMENT' AND e.operation='APPROVE'
             AND e.action_code='APPROVE_RISK_ASSESSMENT'
            """,scope.companyId(),scope.facilityId(),actionId,Timestamp.from(occurredAt),
            eventId,assessmentId,actionId,Timestamp.from(occurredAt));
        if(rows!=1)throw new IllegalStateException("Governed Audit scope binding was not established.");
    }
    @Override public List<Event> events(Scope scope,Window window,Boundary after,int limit) {
        if(limit<1||limit>1001)throw new IllegalArgumentException("Audit query limit exceeded.");
        String sql="""
                SELECT s.event_id,s.event_type,e.operation,e.target_id,s.workflow_action_id,s.occurred_at
                FROM public.hidra_audit_t28_event_scope s
                JOIN public.hidra_audit_event e ON e.id=s.event_id
                WHERE s.company_id=? AND s.facility_id=? AND s.event_type=?
                 AND s.occurred_at>=? AND s.occurred_at<?
                """+(after==null?"":" AND (s.occurred_at,s.event_id)<(?,?) ")+
                " ORDER BY s.occurred_at DESC,s.event_id DESC LIMIT ?";
        List<Object> params=new ArrayList<>(List.of(scope.companyId(),scope.facilityId(),
                window.eventType(),Timestamp.from(window.from()),Timestamp.from(window.to())));
        if(after!=null){params.add(Timestamp.from(after.occurredAt()));params.add(after.eventId());}
        params.add(limit);
        return jdbc.query(sql,(rs,n)->new Event(rs.getString(1),rs.getString(2),rs.getString(3),
                rs.getString(4),rs.getString(5),rs.getTimestamp(6).toInstant()),params.toArray());
    }
    @Override public void lockRequest(Criteria criteria,String requester) {
        String key=String.join("\u001f",criteria.scope().companyId(),criteria.scope().facilityId(),
                requester,criteria.requestKey());
        jdbc.queryForObject("SELECT pg_advisory_xact_lock(hashtextextended(?,0))",Object.class,key);
    }
    private static Manifest mapped(ResultSet rs)throws SQLException{
        var scope=new Scope(rs.getString("company_id"),rs.getString("facility_id"));
        var window=new Window(rs.getString("event_type"),
                rs.getTimestamp("window_from").toInstant(),rs.getTimestamp("window_to").toInstant());
        return new Manifest((UUID)rs.getObject("id"),scope,window,rs.getString("purpose"),
                rs.getString("format"),rs.getString("requester"),rs.getInt("event_count"),
                rs.getInt("byte_length"),rs.getString("sha256"),
                (UUID)rs.getObject("receipt_id"),rs.getTimestamp("completed_at").toInstant());
    }
    private Optional<Manifest> one(String sql,Object... args) {
        List<Manifest> rows=jdbc.query(sql,(rs,n)->mapped(rs),args);
        if(rows.size()>1)throw new IllegalStateException("Nonunique Audit export key.");
        return rows.stream().findFirst();
    }
    private static final String HEAD="""
        SELECT id,company_id,facility_id,requester,request_key,purpose,format,event_type,
               window_from,window_to,event_count,byte_length,sha256,receipt_id,completed_at
        FROM public.hidra_audit_t28_export
        """;
    @Override public Optional<Manifest> existing(Criteria criteria,String requester) {
        return one(HEAD+" WHERE company_id=? AND facility_id=? AND requester=? AND request_key=?",
                criteria.scope().companyId(),criteria.scope().facilityId(),requester,criteria.requestKey());
    }
    @Override public Optional<Manifest> manifest(Scope scope,String requester,UUID id) {
        return one(HEAD+" WHERE id=? AND company_id=? AND facility_id=? AND requester=?",
                id,scope.companyId(),scope.facilityId(),requester);
    }
    @Override public Optional<Artifact> artifact(Scope scope,String requester,UUID id) {
        Optional<Manifest> m=manifest(scope,requester,id);
        if(m.isEmpty())return Optional.empty();
        List<byte[]> rows=jdbc.query("SELECT content FROM "+X+
            " WHERE id=? AND company_id=? AND facility_id=? AND requester=?",
            (rs,n)->rs.getBytes(1),id,scope.companyId(),scope.facilityId(),requester);
        if(rows.size()!=1)throw new IllegalStateException("Missing committed Audit export bytes.");
        return Optional.of(new Artifact(m.get(),rows.getFirst()));
    }
    @Override public Manifest store(Criteria criteria,String actor,byte[] bytes,int count,
            String digest,UUID receiptId) {
        UUID id=UUID.randomUUID();
        Instant at=Instant.now().truncatedTo(ChronoUnit.MICROS);
        Scope scope=criteria.scope();Window w=criteria.window();
        jdbc.update("""
                INSERT INTO public.hidra_audit_t28_export
                  (id,company_id,facility_id,requester,request_key,purpose,format,
                   event_type,window_from,window_to,event_count,byte_length,sha256,content,
                   receipt_id,completed_at)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                """,id,scope.companyId(),scope.facilityId(),actor,criteria.requestKey(),
                criteria.purpose(),ScopedAuditEvidence.FORMAT,w.eventType(),
                Timestamp.from(w.from()),Timestamp.from(w.to()),count,bytes.length,digest,
                bytes,receiptId,Timestamp.from(at));
        return new Manifest(id,scope,w,criteria.purpose(),ScopedAuditEvidence.FORMAT,actor,
                count,bytes.length,digest,receiptId,at);
    }
    @Override public void receipt(Scope scope,String actor,String action,String outcome,
            UUID exportId,String locatorDigest,UUID receiptId) {
        int n=jdbc.update("""
                INSERT INTO public.hidra_audit_t28_access_receipt
                  (id,company_id,facility_id,actor_id,action,outcome,export_id,locator_digest,accessed_at)
                VALUES (?,?,?,?,?,?,?,?,?)
                """,receiptId,scope.companyId(),scope.facilityId(),actor,action,outcome,
                exportId,locatorDigest,Timestamp.from(Instant.now()));
        if(n!=1)throw new IllegalStateException("Audit access evidence was not committed.");
    }
}
