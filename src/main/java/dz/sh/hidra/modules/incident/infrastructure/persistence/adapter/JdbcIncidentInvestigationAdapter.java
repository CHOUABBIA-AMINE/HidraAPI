/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcIncidentInvestigationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 * @Description : Atomic T16 locked Incident transitions, canonical resolution and immutable receipts.
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.in.IncidentInvestigationUseCase;
import dz.sh.hidra.modules.incident.application.port.out.IncidentInvestigationStorePort;
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
public class JdbcIncidentInvestigationAdapter implements IncidentInvestigationStorePort {
    private final JdbcTemplate jdbc;
    private static final String HEAD="public.hidra_incident_investigation";
    private static final String EVENT="public.hidra_incident_investigation_event";

    public JdbcIncidentInvestigationAdapter(JdbcTemplate jdbc) {
        this.jdbc=Objects.requireNonNull(jdbc);
    }
    private static Snapshot map(ResultSet rs, int ignored) throws SQLException {
        return new Snapshot(rs.getString("incident_id"),rs.getString("status"),rs.getString("state"),
                rs.getLong("version"),rs.getString("investigator_id"),rs.getString("submitted_by_id"),
                rs.getString("reviewed_by_id"),rs.getString("resolution_type_id"),
                rs.getString("resolution_summary"),rs.getBoolean("corrective_required"),
                rs.getBoolean("preventive_required"),rs.getBoolean("has_evidence"),
                rs.getBoolean("has_action"),rs.getBoolean("has_root_cause"));
    }
    private Snapshot query(String id,boolean locked) {
        var rows=jdbc.query("""
            select i.id as incident_id,i.status,h.state,
              coalesce(h.version,0) as version,h.investigator_id,h.submitted_by_id,h.reviewed_by_id,
              h.resolution_type_id,h.resolution_summary,
              coalesce(h.corrective_required,false) as corrective_required,
              coalesce(h.preventive_required,false) as preventive_required,
              coalesce(h.has_evidence,false) as has_evidence,
              coalesce(h.has_action,false) as has_action,
              exists(select 1 from public.hidra_incident_root_cause_analysis root where root.incident_id=i.id) as has_root_cause
            from public.hidra_incident i left join public.hidra_incident_investigation h
              on h.incident_id=i.id where i.id=?
            """ + (locked?" for update of i":""),JdbcIncidentInvestigationAdapter::map,id);
        if(rows.isEmpty())throw new NoSuchElementException("Protected Incident unavailable.");
        var s=rows.getFirst();
        return s.state()==null ? new Snapshot(id,s.parentStatus(),"NEW",0,
                null,null,null,null,null,false,false,false,false,false) : s;
    }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Snapshot lock(String id) {
        var s=query(id,true);
        jdbc.update("""
            insert into public.hidra_incident_investigation(incident_id,state,version,updated_at)
            values (?,'NEW',0,now()) on conflict (incident_id) do nothing
            """,id);
        return s;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Snapshot view(String id) {return query(id,false);}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<IncidentInvestigationUseCase.Receipt> replay(String id,String key,String digest) {
        var rows=jdbc.query("""
            select id,incident_id,operation,version,state_after,actor_id,recorded_at,command_sha256
            from public.hidra_incident_investigation_event where incident_id=? and command_key=?
            """,(rs,n)->{
                if(!digest.equals(rs.getString("command_sha256")))
                    throw new IllegalStateException("Conflicting Incident command replay.");
                return new IncidentInvestigationUseCase.Receipt(rs.getString("incident_id"),
                        rs.getString("id"),rs.getString("operation"),rs.getLong("version"),
                        rs.getString("state_after"),rs.getString("actor_id"),
                        rs.getTimestamp("recorded_at").toInstant(),false);
            },id,key);
        return rows.stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public IncidentInvestigationUseCase.Receipt append(Snapshot s,
            IncidentInvestigationUseCase.Command cmd,String actor,String hash,Instant now) {
        Instant at=now.truncatedTo(ChronoUnit.MICROS);
        String op=cmd.operation();
        String after=switch(op) {
            case "ASSIGN" -> "ASSIGNED";
            case "EVIDENCE","ACTION" -> "INVESTIGATING";
            case "SUBMIT" -> "SUBMITTED";
            case "REVIEW" -> "APPROVE".equals(cmd.direction())?"APPROVED":"INVESTIGATING";
            case "CLOSE" -> "CLOSED";
            default -> throw new IllegalArgumentException("Unknown Incident transition.");
        };
        String investigator="ASSIGN".equals(op)?actor:s.investigatorId();
        String submitted="SUBMIT".equals(op)?actor:
                "REVIEW".equals(op)&&"REJECT".equals(cmd.direction())?null:s.submittedById();
        String reviewer="REVIEW".equals(op)&&"APPROVE".equals(cmd.direction())?actor:
                "SUBMIT".equals(op)||"REVIEW".equals(op)?null:s.reviewedById();
        String type="SUBMIT".equals(op)?cmd.resolutionTypeId():s.resolutionTypeId();
        String summary="SUBMIT".equals(op)?cmd.detail():s.resolutionSummary();
        boolean corrective="SUBMIT".equals(op)?cmd.corrective():s.corrective();
        boolean preventive="SUBMIT".equals(op)?cmd.preventive():s.preventive();
        boolean evidence=s.hasEvidence()||"EVIDENCE".equals(op);
        boolean action=s.hasAction()||"ACTION".equals(op);

        if("SUBMIT".equals(op) && !Boolean.TRUE.equals(jdbc.queryForObject("""
                select exists(select 1 from public.hidra_incident_catalog_entry
                 where id=? and catalog_name='RESOLUTION_TYPE' and active)
                """,Boolean.class,type)))
            throw new IllegalArgumentException("Inactive or unknown Incident resolution type.");
        if("EVIDENCE".equals(op)&&"ROOT_CAUSE".equals(cmd.referenceType())) {
            var valid=jdbc.queryForObject("""
                select exists(select 1 from public.hidra_incident_catalog_entry
                  where id=? and catalog_name='ROOT_CAUSE_CATEGORY' and active)
                """,Boolean.class,cmd.referenceId());
            if(!Boolean.TRUE.equals(valid))throw new IllegalArgumentException("Invalid Incident root cause category.");
            jdbc.update("""
                insert into public.hidra_incident_root_cause_analysis
                 (id,incident_id,root_cause_category_id,summary,performed_by_actor_id,performed_at)
                values (?,?,?,?,?,?)
                """,UUID.randomUUID().toString(),s.incidentId(),cmd.referenceId(),
                cmd.detail(),actor,Timestamp.from(at));
        }
        if("EVIDENCE".equals(op)&&!"ROOT_CAUSE".equals(cmd.referenceType())) {
            // HMR-090 closes only over persisted evidence links, never a T16 boolean alone.
            jdbc.update("""
                insert into public.hidra_incident_evidence_link
                 (id,incident_id,evidence_type,evidence_reference_id,
                  evidence_summary,evidence_timestamp,attached_by_actor_id,attached_at)
                values (?,?,?,?,?,?,?,?)
                """,UUID.randomUUID().toString(),s.incidentId(),cmd.referenceType(),
                    cmd.referenceId(),cmd.detail(),Timestamp.from(at),actor,Timestamp.from(at));
        }
        if("ACTION".equals(op)) {
            var valid=jdbc.queryForObject("""
                select exists(select 1 from public.hidra_incident_catalog_entry
                  where id=? and catalog_name='RESPONSE_ACTION_TYPE' and active)
                """,Boolean.class,cmd.referenceId());
            if(!Boolean.TRUE.equals(valid))throw new IllegalArgumentException("Invalid Incident response action type.");
            jdbc.update("""
                insert into public.hidra_incident_response_action
                 (id,incident_id,action_type_id,action_status,description,performed_by_actor_id,
                  performed_by_actor_name_snapshot,started_at,completed_at,created_at,updated_at)
                values (?,?,?,'COMPLETED',?,?,?,?,?,?,?)
                """,UUID.randomUUID().toString(),s.incidentId(),cmd.referenceId(),cmd.detail(),
                    actor,actor,Timestamp.from(at),Timestamp.from(at),Timestamp.from(at),Timestamp.from(at));
        }
        if("REVIEW".equals(op) && "APPROVE".equals(cmd.direction())) {
            jdbc.update("""
                insert into public.hidra_incident_resolution
                 (id,incident_id,resolution_type_id,resolution_summary,corrective_action_required,
                  preventive_action_required,resolved_by_actor_id,resolved_at)
                values (?,?,?,?,?,?,?,?)
                """,UUID.randomUUID().toString(),s.incidentId(),type,summary,
                    corrective,preventive,s.submittedById(),Timestamp.from(at));
        }
        if("ASSIGN".equals(op))jdbc.update("""
                update public.hidra_incident set status='ASSIGNED',
                 responsible_actor_id=?,responsible_actor_name_snapshot=?,updated_at=? where id=? and status in ('OPEN','TRIAGED')
                """,actor,actor,Timestamp.from(at),s.incidentId());
        if(("EVIDENCE".equals(op)||"ACTION".equals(op))
                &&"ASSIGNED".equals(s.parentStatus()))
            jdbc.update("update public.hidra_incident set status='IN_PROGRESS',updated_at=? where id=? and status='ASSIGNED'",
                    Timestamp.from(at),s.incidentId());
        if("REVIEW".equals(op)&&"APPROVE".equals(cmd.direction()))
            jdbc.update("update public.hidra_incident set status='RESOLVED',resolved_at=?,updated_at=? where id=? and status='IN_PROGRESS'",
                    Timestamp.from(at),Timestamp.from(at),s.incidentId());

        int changed=jdbc.update("""
            update public.hidra_incident_investigation
             set state=?,version=?,investigator_id=?,submitted_by_id=?,reviewed_by_id=?,
                 resolution_type_id=?,resolution_summary=?,corrective_required=?,preventive_required=?,
                 has_evidence=?,has_action=?,updated_at=?
             where incident_id=? and version=?
             """,after,s.version()+1,investigator,submitted,reviewer,type,summary,corrective,preventive,
                evidence,action,Timestamp.from(at),s.incidentId(),s.version());
        if(changed!=1)throw new IllegalStateException("Stale Incident investigation transition.");
        String id=UUID.randomUUID().toString();
        jdbc.update("""
            insert into public.hidra_incident_investigation_event
             (id,incident_id,version,command_key,command_sha256,operation,actor_id,
              reference_type,reference_id,evidence_direction,detail,state_before,state_after,recorded_at)
            values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """,id,s.incidentId(),s.version()+1,cmd.requestId(),hash,op,actor,cmd.referenceType(),
                cmd.referenceId(),cmd.direction(),cmd.detail(),s.state(),after,Timestamp.from(at));
        return new IncidentInvestigationUseCase.Receipt(s.incidentId(),id,op,s.version()+1,
                after,actor,at,false);
    }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<IncidentInvestigationUseCase.Event> history(String incidentId) {
        return jdbc.query("""
            select * from public.hidra_incident_investigation_event
             where incident_id=? order by version
            """,(rs,n)->new IncidentInvestigationUseCase.Event(rs.getString("id"),rs.getLong("version"),
                rs.getString("operation"),rs.getString("actor_id"),rs.getString("command_key"),
                rs.getString("reference_type"),rs.getString("reference_id"),rs.getString("evidence_direction"),
                rs.getString("detail"),rs.getString("state_before"),rs.getString("state_after"),
                rs.getTimestamp("recorded_at").toInstant()),incidentId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean reviewedForClosure(String incidentId,String closer) {
        var s=view(incidentId);
        if("NEW".equals(s.state()))return true; // HMR-090 legacy case, no T16 head.
        // A governed T16 case must pass the protected T16 CLOSE transition first.
        return "CLOSED".equals(s.state())
                &&s.reviewedById()!=null&&!s.reviewedById().equals(s.investigatorId())
                &&closer!=null&&!closer.equals(s.investigatorId());
    }
}
