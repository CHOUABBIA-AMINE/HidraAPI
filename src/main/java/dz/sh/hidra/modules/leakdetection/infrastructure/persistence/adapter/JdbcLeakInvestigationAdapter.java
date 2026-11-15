/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcLeakInvestigationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 * @Description : Atomic Leak candidate/case parent locks, versioned disposition and durable receipts.
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.in.LeakInvestigationUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakInvestigationStorePort;
import jakarta.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLeakInvestigationAdapter implements LeakInvestigationStorePort {
    private final JdbcTemplate jdbc;
    private final EntityManager entityManager;
    private static final String HEAD="public.hidra_leak_detection_investigation";
    private static final String EVENT="public.hidra_leak_detection_investigation_event";
    public JdbcLeakInvestigationAdapter(JdbcTemplate jdbc,EntityManager entityManager) {
        this.jdbc=Objects.requireNonNull(jdbc);this.entityManager=Objects.requireNonNull(entityManager);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void serializeCandidate(String id) {
        if(jdbc.query("select id from public.hidra_leak_detection_candidate where id=? for update",
                (rs,n)->rs.getString("id"),id).isEmpty())
            throw new NoSuchElementException("Protected Leak candidate unavailable.");
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<String> caseForCandidate(String id) {
        return jdbc.query("select case_id from "+HEAD+" where candidate_id=?",
                (rs,n)->rs.getString("case_id"),id).stream().findFirst();
    }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void register(String caseId,String candidateId,String requestId,String hash,String actor,Instant time) {
        entityManager.flush(); // Flush existing same-owner JPA case before the governed JDBC FK.
        Instant at=time.truncatedTo(ChronoUnit.MICROS);
        jdbc.update("""
            insert into public.hidra_leak_detection_investigation
             (case_id,candidate_id,opening_request_key,opening_sha256,state,version,updated_at)
            values (?,?,?,?,'NEW',0,?)
            """,caseId,candidateId,requestId,hash,Timestamp.from(at));
        jdbc.update("""
            insert into public.hidra_leak_detection_investigation_event
             (id,case_id,version,command_key,command_sha256,operation,actor_id,
              detail,state_before,state_after,recorded_at)
            values (?,?,0,?,?,'OPEN',?,'Human case creation from source-qualified candidate','NEW','NEW',?)
            """,UUID.randomUUID().toString(),caseId,requestId,hash,actor,Timestamp.from(at));
        jdbc.update("""
            insert into public.hidra_leak_detection_case_status_history
             (id,case_id,old_status,new_status,reason_text,changed_by_actor_id,changed_at,correlation_id)
            values (?,?,null,'OPEN','T16 source-qualified suspected case',?,?,?)
            """,UUID.randomUUID().toString(),caseId,actor,Timestamp.from(at),requestId);
    }
    private static Snapshot map(ResultSet r,int ignored) throws SQLException {
        return new Snapshot(r.getString("case_id"),r.getString("candidate_id"),r.getString("status"),
                r.getString("state"),r.getLong("version"),r.getString("investigator_id"),
                r.getString("submitted_by_id"),r.getString("reviewed_by_id"),r.getString("disposition"),
                r.getBoolean("has_evidence"),r.getBoolean("has_action"));
    }
    private Snapshot query(String id,boolean lock) {
        var rows=jdbc.query("""
            select h.*,c.status from public.hidra_leak_detection_investigation h
              join public.hidra_leak_detection_case c on c.id=h.case_id
              join public.hidra_leak_detection_candidate candidate
                on candidate.id=h.candidate_id
              where h.case_id=? and c.primary_candidate_id=h.candidate_id
                and c.topology_asset_type=candidate.topology_asset_type
                and c.topology_asset_id=candidate.topology_asset_id
            """ + (lock?" for update of c,h":""),JdbcLeakInvestigationAdapter::map,id);
        if(rows.isEmpty())throw new NoSuchElementException("Protected Leak case unavailable.");
        return rows.getFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Snapshot lock(String caseId){return query(caseId,true);}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Snapshot view(String caseId){return query(caseId,false);}

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<LeakInvestigationUseCase.Receipt> replay(String caseId,String key,String hash) {
        return jdbc.query("""
            select id,case_id,operation,version,state_after,actor_id,recorded_at,command_sha256
             from public.hidra_leak_detection_investigation_event where case_id=? and command_key=?
            """,(rs,n)->{
                if(!hash.equals(rs.getString("command_sha256")))
                    throw new IllegalStateException("Conflicting Leak case command replay.");
                return new LeakInvestigationUseCase.Receipt(rs.getString("case_id"),
                        rs.getString("id"),rs.getString("operation"),rs.getLong("version"),
                        rs.getString("state_after"),rs.getString("actor_id"),
                        rs.getTimestamp("recorded_at").toInstant(),false);
            },caseId,key).stream().findFirst();
    }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public LeakInvestigationUseCase.Receipt append(Snapshot s,LeakInvestigationUseCase.Command cmd,
            String actor,String hash,Instant now) {
        Instant at=now.truncatedTo(ChronoUnit.MICROS);
        String op=cmd.operation();
        String next=switch(op) {
            case "ASSIGN" -> "ASSIGNED";
            case "EVIDENCE","ACTION" -> "INVESTIGATING";
            case "SUBMIT" -> "SUBMITTED";
            case "REVIEW" -> "REJECT".equals(cmd.direction())?"INVESTIGATING":
                    "INSUFFICIENT_EVIDENCE".equals(s.disposition())?"INSUFFICIENT_EVIDENCE":"REVIEWED";
            default -> throw new IllegalArgumentException("Unknown Leak investigation operation.");
        };
        String investigator="ASSIGN".equals(op)?actor:s.investigatorId();
        String submitted="SUBMIT".equals(op)?actor:
                "REVIEW".equals(op)&&"REJECT".equals(cmd.direction())?null:s.submittedById();
        String reviewer="REVIEW".equals(op)&&"APPROVE".equals(cmd.direction())?actor:
                "SUBMIT".equals(op)||"REVIEW".equals(op)?null:s.reviewedById();
        String disposition="SUBMIT".equals(op)?cmd.disposition():s.disposition();
        boolean hasEvidence=s.hasEvidence()||"EVIDENCE".equals(op);
        boolean hasAction=s.hasAction()||"ACTION".equals(op);
        if("ACTION".equals(op))jdbc.update("""
            insert into public.hidra_leak_detection_verification_action
             (id,candidate_id,case_id,action_type,assigned_actor_id,status,
              requested_at,started_at,completed_at,result_text,correlation_id)
            values (?,?,?,?,?,'COMPLETED',?,?,?,?,?)
            """,UUID.randomUUID().toString(),s.candidateId(),s.caseId(),cmd.referenceType(),actor,
                Timestamp.from(at),Timestamp.from(at),Timestamp.from(at),cmd.detail(),UUID.randomUUID().toString());
        if("EVIDENCE".equals(op)||"ACTION".equals(op)) {
            jdbc.update("""
                update public.hidra_leak_detection_case set status='UNDER_INVESTIGATION',updated_at=?
                where id=? and status in ('OPEN','UNDER_INVESTIGATION')
                """,Timestamp.from(at),s.caseId());
        }
        if("REVIEW".equals(op)&&"APPROVE".equals(cmd.direction()) && !"INSUFFICIENT_EVIDENCE".equals(s.disposition())) {
            // Documentary human decision, not an automatic SCADA command or field leak assertion.
            int changed=jdbc.update("""
                update public.hidra_leak_detection_case
                 set status=?,closed_at=?,closed_by_actor_id=?,updated_at=?
                 where id=? and status='UNDER_INVESTIGATION'
                """,s.disposition(),Timestamp.from(at),actor,Timestamp.from(at),s.caseId());
            if(changed!=1)throw new IllegalStateException("Leak final disposition lost its parent.");
        }
        int changed=jdbc.update("""
            update public.hidra_leak_detection_investigation
              set state=?,version=?,investigator_id=?,submitted_by_id=?,reviewed_by_id=?,
                  disposition=?,has_evidence=?,has_action=?,updated_at=?
              where case_id=? and version=?
            """,next,s.version()+1,investigator,submitted,reviewer,disposition,
                hasEvidence,hasAction,Timestamp.from(at),s.caseId(),s.version());
        String newCaseStatus=("EVIDENCE".equals(op)||"ACTION".equals(op))
                ?"UNDER_INVESTIGATION":
                "REVIEW".equals(op)&&"APPROVE".equals(cmd.direction())
                  &&!"INSUFFICIENT_EVIDENCE".equals(s.disposition())?s.disposition():s.parentStatus();
        if(!s.parentStatus().equals(newCaseStatus)) {
            jdbc.update("""
                insert into public.hidra_leak_detection_case_status_history
                 (id,case_id,old_status,new_status,reason_text,changed_by_actor_id,changed_at,correlation_id)
                values (?,?,?,?,?,?,?,?)
                """,UUID.randomUUID().toString(),s.caseId(),s.parentStatus(),newCaseStatus,
                cmd.detail(),actor,Timestamp.from(at),cmd.requestId());
        }
        if(changed!=1)throw new IllegalStateException("Stale Leak case investigation.");
        String eventId=UUID.randomUUID().toString();
        jdbc.update("""
            insert into public.hidra_leak_detection_investigation_event
             (id,case_id,version,command_key,command_sha256,operation,actor_id,
              reference_type,reference_id,evidence_direction,detail,state_before,state_after,recorded_at)
            values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """,eventId,s.caseId(),s.version()+1,cmd.requestId(),hash,op,actor,
                cmd.referenceType(),cmd.referenceId(),cmd.direction(),cmd.detail(),
                s.state(),next,Timestamp.from(at));
        return new LeakInvestigationUseCase.Receipt(s.caseId(),eventId,op,s.version()+1,
                next,actor,at,false);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<LeakInvestigationUseCase.Event> history(String caseId) {
        return jdbc.query("""
            select * from public.hidra_leak_detection_investigation_event
             where case_id=? order by version
            """,(rs,n)->new LeakInvestigationUseCase.Event(rs.getString("id"),rs.getLong("version"),
                rs.getString("operation"),rs.getString("actor_id"),rs.getString("command_key"),
                rs.getString("reference_type"),rs.getString("reference_id"),rs.getString("evidence_direction"),
                rs.getString("detail"),rs.getString("state_before"),rs.getString("state_after"),
                rs.getTimestamp("recorded_at").toInstant()),caseId);
    }
}
