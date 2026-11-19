/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcHseCapaLifecycleStoreAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 * @Description : Serializes T17 HSE parent/CAPA mutation and protects immutable receipts.
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.in.HseCapaLifecycleUseCase.*;
import dz.sh.hidra.modules.hse.application.port.out.*;
import dz.sh.hidra.modules.hse.domain.model.*;
import dz.sh.hidra.modules.hse.domain.value.CapaStatus;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JdbcHseCapaLifecycleStoreAdapter implements HseCapaLifecycleStorePort {
    private final JdbcTemplate jdbc;
    public JdbcHseCapaLifecycleStoreAdapter(JdbcTemplate jdbc){this.jdbc=Objects.requireNonNull(jdbc);}
    private static Instant time(ResultSet r,String key)throws SQLException{
        Timestamp t=r.getTimestamp(key);return t==null?null:t.toInstant();
    }
    private static String id(){return UUID.randomUUID().toString();}
    private static IllegalStateException conflict(){return new IllegalStateException("Conflicting HSE record revision.");}
    private static NoSuchElementException hidden(){return new NoSuchElementException("Protected HSE unavailable.");}
    private static final String S="public.hidra_hse_t17_case_scope";
    private static final String H="public.hidra_hse_t17_capa_state";
    private static final String E="public.hidra_hse_t17_capa_event";
    private static final String D="public.hidra_hse_t17_capa_evidence";
    private static final String V="public.hidra_hse_t17_capa_verification";
    private static final String C="public.hidra_hse_t17_case_close_receipt";
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void serializeEnrollment(String company,String facility,String request){
        jdbc.queryForObject("SELECT pg_advisory_xact_lock(hashtextextended(?::text,0))",Object.class,
            "t17:"+company+":"+facility+":"+request);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Scope> created(String company,String facility,String request){
        return jdbc.query("SELECT * FROM "+S+" WHERE company_id=? AND facility_id=? AND create_request_id=?",
            (r,n)->scope(r),company,facility,request).stream().findFirst();
    }
    private static Scope scope(ResultSet r)throws SQLException{
        return new Scope(r.getString("case_id"),r.getString("company_id"),r.getString("facility_id"),
                r.getString("incident_id"),r.getString("create_request_id"),r.getString("create_digest"),
                r.getString("created_by_actor_id"),time(r,"created_at"));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void bind(Scope binding){
        jdbc.update("INSERT INTO "+S+"(case_id,company_id,facility_id,incident_id,"+
                "create_request_id,create_digest,created_by_actor_id,created_at) VALUES(?,?,?,?,?,?,?,?)",
                binding.caseId(),binding.companyId(),binding.facilityId(),binding.incidentId(),
                binding.createRequestId(),binding.createDigest(),binding.actorId(),
                Timestamp.from(binding.createdAt()));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Scope> scope(String caseId){
        return jdbc.query("SELECT * FROM "+S+" WHERE case_id=?",(r,n)->scope(r),caseId)
                .stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Scope> scopeByCapa(String capaId) {
        return jdbc.query("SELECT s.* FROM "+H+" c JOIN "+S+" s ON s.case_id=c.case_id WHERE c.capa_id=?",
                (r,n) -> scope(r), capaId).stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void lockCase(String caseId){
        List<String> locked=jdbc.query("SELECT id FROM public.hidra_hse_case WHERE id=? FOR UPDATE",
                (r,n)->r.getString(1),caseId);
        if(locked.size()!=1||scope(caseId).isEmpty())throw hidden();
    }
    private static CaseView caseView(ResultSet r)throws SQLException{
        return new CaseView(r.getString("case_id"),r.getString("company_id"),r.getString("facility_id"),
                r.getString("incident_id"),r.getString("case_number"),r.getString("title"),
                r.getString("status"),r.getString("created_by_actor_id"),time(r,"created_at"),
                r.getString("closure_id"));
    }
    private static String caseProjection(){
        return "SELECT s.*,h.case_number,h.title,h.status,cl.closure_id FROM "+S+
                " s JOIN public.hidra_hse_case h ON h.id=s.case_id LEFT JOIN "+C+
                " cl ON cl.case_id=s.case_id";
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public CaseView caseView(String caseId){
        return jdbc.query(caseProjection()+" WHERE s.case_id=?",(r,n)->caseView(r),caseId)
                .stream().findFirst().orElseThrow(JdbcHseCapaLifecycleStoreAdapter::hidden);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void visitCases(String company,String facility,String caseNumber,
            java.util.function.Consumer<CaseView> visitor){
        Objects.requireNonNull(visitor,"Scoped HSE visitor required.");
        String sql=caseProjection()+" WHERE s.company_id=? AND s.facility_id=?"+
                (caseNumber==null?"":" AND h.case_number=?")+
                " ORDER BY s.created_at,s.case_id";
        jdbc.query(connection->{
            PreparedStatement statement=connection.prepareStatement(sql,
                    ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            statement.setFetchSize(128);
            statement.setString(1,company);
            statement.setString(2,facility);
            if(caseNumber!=null)statement.setString(3,caseNumber);
            return statement;
        },(RowCallbackHandler) row->visitor.accept(caseView(row)));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void addCapa(String capaId,String caseId,String proposer,String owner,String requestId,
            String digest,String actor,Instant at){
        jdbc.update("INSERT INTO "+H+"(capa_id,case_id,status,revision,proposer_id,owner_id,updated_at) "+
                "VALUES(?,?,'PROPOSED',0,?,?,?)",capaId,caseId,proposer,owner,Timestamp.from(at));
        append(new Receipt(id(),caseId,capaId,requestId,digest,actor,
                "PROPOSE",null,"PROPOSED",0,null,at),"CAPA proposal");
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void lockCapa(String capaId){
        if(jdbc.query("SELECT id FROM public.hidra_hse_capa WHERE id=? FOR UPDATE",
                (r,n)->r.getString(1),capaId).size()!=1)throw hidden();
    }
    private static State state(ResultSet r)throws SQLException{
        return new State(r.getString("capa_id"),r.getString("case_id"),
                CapaStatus.valueOf(r.getString("status")),r.getLong("revision"),
                r.getString("proposer_id"),r.getString("owner_id"),r.getString("verifier_id"),
                r.getString("evidence_id"));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public State state(String caseId,String capaId){
        return jdbc.query("SELECT * FROM "+H+" WHERE case_id=? AND capa_id=?",
                (r,n)->state(r),caseId,capaId).stream().findFirst()
                .orElseThrow(JdbcHseCapaLifecycleStoreAdapter::hidden);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<State> states(String caseId){
        return jdbc.query("SELECT * FROM "+H+" WHERE case_id=? ORDER BY capa_id",
                (r,n)->state(r),caseId);
    }
    private static CapaView capaView(ResultSet r)throws SQLException{
        return new CapaView(r.getString("capa_id"),r.getString("case_id"),
                r.getString("action_number"),r.getString("title"),r.getString("status"),
                r.getLong("revision"),r.getString("proposer_id"),r.getString("owner_id"),
                r.getString("verifier_id"),r.getString("evidence_id"));
    }
    private static String capaProjection(){return "SELECT s.*,c.action_number,c.title FROM "+H+
            " s JOIN public.hidra_hse_capa c ON c.id=s.capa_id";}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Page<CapaView> capas(String caseId,int page,int size,String actionNumber){
        String sql="WITH eligible AS ("+
                " SELECT s.*,c.action_number,c.title,c.created_at AS sort_created_at FROM "+H+
                " s JOIN public.hidra_hse_capa c ON c.id=s.capa_id"+
                " WHERE s.case_id=? AND (? IS NULL OR c.action_number=?)"+
                "), tally AS (SELECT count(*) AS total FROM eligible),"+
                " sliced AS (SELECT * FROM eligible ORDER BY sort_created_at,capa_id LIMIT ? OFFSET ?)"+
                " SELECT tally.total,sliced.* FROM tally LEFT JOIN sliced ON TRUE";
        List<CapaView> views=new ArrayList<>();
        long[] total={0};
        jdbc.query(sql,(RowCallbackHandler) row->{
            total[0]=row.getLong("total");
            if(row.getString("capa_id")!=null)views.add(capaView(row));
        },caseId,actionNumber,actionNumber,size,(long)page*size);
        return new Page<>(List.copyOf(views),total[0],page,size);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public CapaView capaView(String caseId,String capaId){
        return jdbc.query(capaProjection()+" WHERE s.case_id=? AND s.capa_id=?",
            (r,n)->capaView(r),caseId,capaId).stream().findFirst()
            .orElseThrow(JdbcHseCapaLifecycleStoreAdapter::hidden);
    }
    private static Receipt receipt(ResultSet r)throws SQLException{
        return new Receipt(r.getString("id"),r.getString("case_id"),r.getString("capa_id"),
                r.getString("request_id"),r.getString("command_sha256"),
                r.getString("actor_id"),r.getString("operation"),r.getString("before_status"),
                r.getString("after_status"),r.getLong("revision"),r.getString("reference_id"),
                time(r,"occurred_at"));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Receipt> replay(String caseId,String requestId){
        return jdbc.query("SELECT * FROM "+E+" WHERE case_id=? AND request_id=?",
                (r,n)->receipt(r),caseId,requestId).stream().findFirst();
    }
    private void append(Receipt e,String reason){
        jdbc.update("INSERT INTO "+E+"(id,case_id,capa_id,request_id,command_sha256,"+
                "operation,before_status,after_status,revision,actor_id,reason_text,reference_id,occurred_at) "+
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)",e.id(),e.caseId(),e.capaId(),
                e.requestId(),e.digest(),e.operation(),e.beforeState(),e.afterState(),
                e.version(),e.actorId(),reason,e.referenceId(),Timestamp.from(e.at()));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Receipt advance(String caseId,String capaId,String requestId,String digest,
            String actor,String operation,String before,String after,long expected,
            String reference,String reason,Instant at){
        String physical="UPDATE public.hidra_hse_capa SET status=?,updated_at=?";
        String extra="";
        if("COMPLETE".equals(operation))extra=",completed_at=?";
        if("VERIFY".equals(operation)&&"VERIFIED".equals(after))
            extra=",verified_by_actor_id=?,verified_at=?";
        physical+=extra+" WHERE id=? AND hse_case_id=?";
        Object[] values;
        Timestamp ts=Timestamp.from(at);
        if("COMPLETE".equals(operation))values=new Object[]{after,ts,ts,capaId,caseId};
        else if("VERIFY".equals(operation)&&"VERIFIED".equals(after))
            values=new Object[]{after,ts,actor,ts,capaId,caseId};
        else values=new Object[]{after,ts,capaId,caseId};
        if(jdbc.update(physical,values)!=1)throw hidden();
        String set="status=?,revision=revision+1,updated_at=?";
        if("EVIDENCE".equals(operation))set+=",evidence_id=?";
        if("VERIFY".equals(operation)&&"VERIFIED".equals(after))set+=",verifier_id=?";
        Object[] head;
        if("EVIDENCE".equals(operation))head=new Object[]{after,ts,reference,capaId,caseId,expected,before};
        else if("VERIFY".equals(operation)&&"VERIFIED".equals(after))
            head=new Object[]{after,ts,actor,capaId,caseId,expected,before};
        else head=new Object[]{after,ts,capaId,caseId,expected,before};
        if(jdbc.update("UPDATE "+H+" SET "+set+
                " WHERE capa_id=? AND case_id=? AND revision=? AND status=?",head)!=1)
            throw conflict();
        var event=new Receipt(id(),caseId,capaId,requestId,digest,actor,operation,
                before,after,expected+1,reference,at);
        append(event,reason);
        return event;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void setOwner(String capaId,String owner){
        if(jdbc.update("UPDATE "+H+" SET owner_id=? WHERE capa_id=?",owner,capaId)!=1)
            throw hidden();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void attach(HseCapaEvidence e){
        jdbc.update("INSERT INTO "+D+"(id,capa_id,document_id,version_id,acceptance_id,"+
                "checksum_sha256,content_length,company_id,facility_id,actor_id,attached_at) "+
                "VALUES(?,?,?,?,?,?,?,?,?,?,?)",e.id(),e.capaId(),e.documentId(),e.versionId(),
                e.acceptanceId(),e.sha256(),e.contentLength(),e.companyId(),e.facilityId(),
                e.actorId(),Timestamp.from(e.attachedAt()));
    }
    private static HseCapaEvidence evidence(ResultSet r)throws SQLException{
        return new HseCapaEvidence(r.getString("id"),r.getString("capa_id"),
            r.getString("document_id"),r.getString("version_id"),r.getString("acceptance_id"),
            r.getString("checksum_sha256"),r.getLong("content_length"),r.getString("company_id"),
            r.getString("facility_id"),r.getString("actor_id"),time(r,"attached_at"));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<HseCapaEvidence> evidence(String capaId){
        return jdbc.query("SELECT * FROM "+D+" WHERE capa_id=? ORDER BY attached_at,id",
            (r,n)->evidence(r),capaId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void verify(HseCapaVerification f){
        jdbc.update("INSERT INTO "+V+"(id,capa_id,evidence_id,outcome,reason_text,reviewer_id,verified_at)"+
                " VALUES(?,?,?,?,?,?,?)",f.id(),f.capaId(),f.evidenceId(),f.finding().name(),
                f.reason(),f.reviewerId(),Timestamp.from(f.verifiedAt()));
        if(f.effective())jdbc.update("UPDATE "+H+" SET evidence_id=? WHERE capa_id=?",
                f.evidenceId(),f.capaId());
    }
    private static HseCapaVerification verification(ResultSet r)throws SQLException{
        return new HseCapaVerification(r.getString("id"),r.getString("capa_id"),
            r.getString("evidence_id"),
            HseCapaVerification.Finding.valueOf(r.getString("outcome")),
            r.getString("reason_text"),r.getString("reviewer_id"),time(r,"verified_at"));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<HseCapaVerification> verification(String capaId){
        return jdbc.query("SELECT * FROM "+V+" WHERE capa_id=? ORDER BY verified_at,id",
            (r,n)->verification(r),capaId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<String> admittedAssessment(String caseId) {
        return jdbc.query("""
                SELECT a.id FROM public.hidra_hse_t17_capa_event e
                JOIN public.hidra_hse_impact_assessment a
                  ON a.hse_case_id=e.case_id AND a.id=e.reference_id
                WHERE e.case_id=? AND e.operation='ASSESS_IMPACT'
                """, (r,n) -> r.getString(1), caseId).stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void recordAssessment(HseCaseImpactAssessment assessment, String status, long revision,
                                 String requestId, String digest) {
        if (jdbc.query("""
                SELECT t.id FROM public.hidra_hse_catalog_field_policy p
                JOIN public.hidra_hse_catalog_entry t ON t.catalog_name=p.catalog_name
                WHERE p.field_role='CASE_IMPACT_TYPE' AND p.active AND t.active AND t.id=?
                FOR SHARE OF p,t
                """, (r,n) -> r.getString(1), assessment.typeId()).isEmpty()) {
            throw new IllegalArgumentException("Eligible HSE case impact type required.");
        }
        var at = assessment.at().truncatedTo(ChronoUnit.MICROS);
        var timestamp = Timestamp.from(at);
        jdbc.update("""
                INSERT INTO public.hidra_hse_impact_assessment
                    (id,hse_case_id,impact_domain,impact_type_id,severity,description,
                     assessed_by_actor_id,assessed_at,created_at,updated_at)
                VALUES (?,?,?,?,?,?,?,?,?,?)
                """, assessment.id(), assessment.caseId(), assessment.domain().name(),
                assessment.typeId(), assessment.severity().name(), assessment.description(),
                assessment.actorId(), timestamp, timestamp, timestamp);
        append(new Receipt(id(), assessment.caseId(), null, requestId, digest, assessment.actorId(),
                "ASSESS_IMPACT", status, status, revision, assessment.id(), at), assessment.description());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public String closeCase(String caseId,String actorId,String displayName,String reason,
            HseCapaWorkflowPort.Decision decision,String requestId,String digest,
            long expectedVersion,String assessmentId,Instant now){
        long count=jdbc.queryForObject("SELECT count(*) FROM "+E+" WHERE case_id=?",Long.class,caseId);
        if(count!=expectedVersion)throw conflict();
        var rows=states(caseId);
        if(rows.isEmpty()||rows.stream().anyMatch(r->r.status()!=CapaStatus.VERIFIED))
            throw conflict();
        Timestamp at=Timestamp.from(now.truncatedTo(ChronoUnit.MICROS));
        // Historical closure must serialize with every required CAPA.
        for(var s:rows){
            lockCapa(s.capaId());
            if(jdbc.update("UPDATE public.hidra_hse_capa SET status='CLOSED',updated_at=? "+
                "WHERE id=? AND status='VERIFIED'",at,s.capaId())!=1)throw conflict();
            if(jdbc.update("UPDATE "+H+" SET status='CLOSED',revision=revision+1,updated_at=? "+
                "WHERE capa_id=? AND revision=? AND status='VERIFIED'",
                at,s.capaId(),s.version())!=1)throw conflict();
            append(new Receipt(id(),caseId,s.capaId(),id(),digest,actorId,
                    "FINALIZE","VERIFIED","CLOSED",s.version()+1,s.evidenceId(),now),
                    "HSE case closure: "+reason);
        }
        String old=jdbc.query("SELECT status FROM public.hidra_hse_case WHERE id=? FOR UPDATE",
                (r,n)->r.getString(1),caseId).stream().findFirst().orElseThrow(
                        JdbcHseCapaLifecycleStoreAdapter::hidden);
        if("CLOSED".equals(old)||"CANCELLED".equals(old))throw conflict();
        String closureId=id();
        jdbc.update("INSERT INTO public.hidra_hse_closure"+
                "(id,hse_case_id,closure_summary,impact_assessed,capa_completed,evidence_reviewed,"+
                "regulatory_reviewed,closed_by_actor_id,closed_by_display_name_snapshot,"+
                "closed_at,workflow_instance_id) VALUES(?,?,?,true,true,true,false,?,?,?,?)",
                closureId,caseId,reason,actorId,displayName,at,decision.instanceId());
        jdbc.update("INSERT INTO public.hidra_hse_case_status_history"+
                "(id,hse_case_id,old_status,new_status,reason_text,changed_by_actor_id,"+
                "changed_by_display_name_snapshot,changed_at,correlation_id)"+
                " VALUES(?,?,?,'CLOSED',?,?,?,?,?)",id(),caseId,old,reason,actorId,displayName,
                at,closureId);
        jdbc.update("UPDATE public.hidra_hse_case SET status='CLOSED',closed_at=?,"+
            "updated_at=? WHERE id=?",at,at,caseId);
        jdbc.update("INSERT INTO "+C+"(case_id,closure_id,workflow_action_id,workflow_instance_id,"+
                "workflow_digest,actor_id,closed_at,assessment_id) VALUES(?,?,?,?,?,?,?,?)",
                caseId,closureId,decision.actionId(),decision.instanceId(),decision.digest(),actorId,at,assessmentId);
        append(new Receipt(id(),caseId,null,requestId,digest,actorId,"CLOSE",old,"CLOSED",
                expectedVersion+rows.size()+1,closureId,now),reason);
        return closureId;
    }
    private static EventView event(ResultSet r)throws SQLException{
        return new EventView(r.getString("id"),r.getString("case_id"),r.getString("capa_id"),
            r.getString("operation"),r.getString("before_status"),r.getString("after_status"),
            r.getLong("revision"),r.getString("actor_id"),r.getString("reason_text"),
            r.getString("reference_id"),time(r,"occurred_at"));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Page<EventView> history(String caseId,int page,int size){
        long total=jdbc.queryForObject("SELECT count(*) FROM "+E+" WHERE case_id=?",Long.class,caseId);
        return new Page<>(jdbc.query("SELECT * FROM "+E+" WHERE case_id=? ORDER BY occurred_at,id "+
                "LIMIT ? OFFSET ?",(r,n)->event(r),caseId,size,(long)page*size),
                total,page,size);
    }
}
