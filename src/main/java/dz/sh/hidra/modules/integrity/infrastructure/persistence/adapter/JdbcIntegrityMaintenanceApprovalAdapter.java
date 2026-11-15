/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcIntegrityMaintenanceApprovalAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 * @Description : T18 source-locked owner-private immutable maintenance evidence adapter.
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityMaintenanceApprovalStorePort;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JdbcIntegrityMaintenanceApprovalAdapter implements IntegrityMaintenanceApprovalStorePort {
    private final JdbcTemplate jdbc;
    public JdbcIntegrityMaintenanceApprovalAdapter(JdbcTemplate jdbc){this.jdbc=Objects.requireNonNull(jdbc);}

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Source lockSource(String finding,String recommendation,String caseId){
        var r=jdbc.query("""
            select id,source_defect_id,status,title,created_at
            from public.hidra_integrity_recommendation where id=? for update
            """,(rs,n)->new Object[]{rs.getString(2),rs.getString(3),rs.getString(4),rs.getTimestamp(5).toInstant()},recommendation);
        if(r.size()!=1)throw hidden();
        var f=jdbc.query("""
            select linked_defect_id,inspection_run_id,topology_asset_type_code,topology_asset_id
            from public.hidra_integrity_inspection_finding where id=? for share
            """,(rs,n)->new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)},finding);
        if(f.size()!=1)throw hidden();
        var run=jdbc.query("""
            select status,topology_asset_type_code,topology_asset_id
            from public.hidra_integrity_inspection_run where id=? for share
            """,(rs,n)->new String[]{rs.getString(1),rs.getString(2),rs.getString(3)},f.getFirst()[1]);
        if(run.size()!=1)throw hidden();
        var defectId=f.getFirst()[0];
        if(defectId==null||defectId.isBlank())throw hidden();
        var d=jdbc.query("""
            select source_finding_id,topology_asset_type_code,topology_asset_id
            from public.hidra_integrity_pipeline_defect where id=? for share
            """,(rs,n)->new String[]{rs.getString(1),rs.getString(2),rs.getString(3)},defectId);
        if(d.size()!=1)throw hidden();
        var c=jdbc.query("""
            select primary_defect_id,status,topology_asset_type_code,topology_asset_id
            from public.hidra_integrity_case where id=? for share
            """,(rs,n)->new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)},caseId);
        if(c.size()!=1)throw hidden();
        String[] findingRow=f.getFirst(), runRow=run.getFirst(),defectRow=d.getFirst(),caseRow=c.getFirst();
        Object[] rec=r.getFirst();
        return new Source(finding,recommendation,caseId,defectId,
                findingRow[2],findingRow[3],runRow[0],(String)rec[1],caseRow[1],
                findingRow[0],(String)rec[0],caseRow[0],defectRow[0],
                runRow[1],runRow[2],findingRow[2],findingRow[3],
                defectRow[1],defectRow[2],caseRow[2],caseRow[3],
                (String)rec[2],((Instant)rec[3]).toString());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Source lockApprovedSource(String recommendation,String finding,String caseId){
        return lockSource(finding,recommendation,caseId);
    }
    private static final RowMapper<Approval> APPROVAL=(rs,n)->new Approval(
            rs.getString("id"),rs.getString("command_id"),rs.getString("command_sha256"),
            rs.getString("finding_id"),rs.getString("case_id"),rs.getString("defect_id"),
            rs.getString("recommendation_id"),rs.getString("asset_type"),rs.getString("asset_id"),
            rs.getString("company_id"),rs.getString("approver_id"),
            rs.getTimestamp("approved_at").toInstant(),rs.getString("reason"),
            rs.getString("source_version"),rs.getString("work_order_id"),
            rs.getString("verification_id"),rs.getString("verification_status"));
    private static final String APPROVAL_QUERY="""
            select a.*,h.work_order_id,
                   (select v.verification_id from public.hidra_integrity_maintenance_verification v
                       where v.approval_id=a.id order by v.verified_at desc,v.verification_id desc limit 1)
                       as verification_id,
                   (select v.verdict from public.hidra_integrity_maintenance_verification v
                       where v.approval_id=a.id order by v.verified_at desc,v.verification_id desc limit 1)
                       as verification_status
            from public.hidra_integrity_maintenance_approval a
            left join public.hidra_integrity_maintenance_handoff h on h.approval_id=a.id
            """;
    private Optional<Approval> by(String condition,String key){
        List<Approval> rows=jdbc.query(APPROVAL_QUERY+" where "+condition,APPROVAL,key);
        if(rows.size()>1)throw new IllegalStateException("Ambiguous Integrity approval.");
        return rows.stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Approval> byCommand(String id){return by("a.command_id=?",id);}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Approval> byId(String id){return by("a.id=?",id);}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Approval> byRecommendation(String id){return by("a.recommendation_id=?",id);}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Approval approve(Source s,String company,String actor,String reason,String command,String digest,Instant at){
        int n=jdbc.update("""
            update public.hidra_integrity_recommendation set status='APPROVED'
            where id=? and status='PROPOSED' and source_defect_id=?
            """,s.recommendationId(),s.defectId());
        if(n!=1)throw new IllegalStateException("Integrity source has changed.");
        String id=UUID.randomUUID().toString();
        jdbc.update("""
            insert into public.hidra_integrity_maintenance_approval
             (id,command_id,command_sha256,recommendation_id,finding_id,case_id,defect_id,
              asset_type,asset_id,company_id,approver_id,approved_at,reason,source_version)
            values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """,id,command,digest,s.recommendationId(),s.findingId(),s.caseId(),s.defectId(),
                s.assetType(),s.assetId(),company,actor,java.sql.Timestamp.from(at),reason,s.sourceVersion());
        return byId(id).orElseThrow();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void link(String approvalId,String workOrderId){
        var locked=jdbc.queryForList("""
            select id from public.hidra_integrity_maintenance_approval where id=? for update
            """,String.class,approvalId);
        if(locked.size()!=1)throw hidden();
        jdbc.update("""
            insert into public.hidra_integrity_maintenance_handoff(approval_id,work_order_id,linked_at)
            values (?,?,now()) on conflict (approval_id) do nothing
            """,approvalId,workOrderId);
        var matches=jdbc.queryForList("""
            select work_order_id from public.hidra_integrity_maintenance_handoff where approval_id=?
            """,String.class,approvalId);
        if(matches.size()!=1||!workOrderId.equals(matches.getFirst()))
            throw new IllegalStateException("Integrity handoff is already bound to another work order.");
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void recordVerification(String approvalId,String workOrderId,String executionId,
            String verificationId,String actorId,String verdict,String evidence,Instant at){
        var linked=jdbc.queryForList("""
            select h.work_order_id from public.hidra_integrity_maintenance_handoff h
            where h.approval_id=? for update
            """,String.class,approvalId);
        if(linked.size()!=1||!workOrderId.equals(linked.getFirst()))throw hidden();
        if("ACCEPT".equals(verdict)){
            var positive=jdbc.queryForList("""
                select verification_id from public.hidra_integrity_maintenance_verification
                where approval_id=? and verdict='ACCEPT'
                """,String.class,approvalId);
            if(!positive.isEmpty())throw new IllegalStateException("Integrity already has positive verification.");
        }
        jdbc.update("""
            insert into public.hidra_integrity_maintenance_verification
                (verification_id,approval_id,work_order_id,execution_id,verifier_id,verdict,evidence,verified_at)
            values (?,?,?,?,?,?,?,?)
            """,verificationId,approvalId,workOrderId,executionId,actorId,verdict,evidence,
            java.sql.Timestamp.from(at));
    }
    private static NoSuchElementException hidden(){
        return new NoSuchElementException("Protected Integrity source unavailable.");
    }
}
