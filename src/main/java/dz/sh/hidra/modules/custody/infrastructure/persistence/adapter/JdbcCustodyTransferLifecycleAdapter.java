/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcCustodyTransferLifecycleAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 * @Description : Stores immutable T21 decision lineage and exports owner-authorized Workflow targets.
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;
import dz.sh.hidra.modules.custody.application.port.out.CustodyTransferLifecyclePort;
import dz.sh.hidra.modules.custody.application.port.in.CustodyTransferLifecycleUseCase.*;
import dz.sh.hidra.modules.custody.domain.model.CustodyReconciliationSnapshot;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyMeasurementScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.custody.CustodyMeasurementAssetOwnershipContract;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import java.math.BigDecimal;
import java.sql.*;
import java.time.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;
@Repository
public class JdbcCustodyTransferLifecycleAdapter implements CustodyTransferLifecyclePort,WorkflowOwnedTargetLookup {
    private final JdbcTemplate jdbc;
    private final CustodyMeasurementScopedAuthorizationContract identity;
    private final CustodyMeasurementAssetOwnershipContract ownership;
    public JdbcCustodyTransferLifecycleAdapter(JdbcTemplate jdbc,
            CustodyMeasurementScopedAuthorizationContract identity,
            CustodyMeasurementAssetOwnershipContract ownership){
        this.jdbc=Objects.requireNonNull(jdbc);this.identity=Objects.requireNonNull(identity);
        this.ownership=Objects.requireNonNull(ownership);
    }
    private static final String REC="public.hidra_custody_t21_reconciliation";
    private static final String TICKET="public.hidra_custody_t21_ticket";
    private static final String ISSUE="public.hidra_custody_t21_discrepancy";
    private static Instant time(ResultSet r,String column)throws SQLException{
        var value=r.getObject(column,OffsetDateTime.class);
        return value==null?null:value.toInstant();
    }
    private static Scope scope(ResultSet r)throws SQLException{
        return new Scope(r.getString("period_id"),r.getString("agreement_id"),r.getString("transfer_point_id"),
            r.getString("topology_asset_type_code"),r.getString("topology_asset_id"),
            r.getString("status"),time(r,"period_start"),time(r,"period_end"),r.getString("stored_company_id"));
    }
    private Optional<Scope> scopeQuery(String sql,String id){
        var rows=jdbc.query(sql,(row,n)->scope(row),id);
        return rows.size()==1?Optional.of(rows.getFirst()):Optional.empty();
    }
    @Override public Optional<Scope> scopeForPeriod(String id){
        return scopeQuery("""
           SELECT p.id AS period_id,p.agreement_id,p.transfer_point_id,p.status,
                  p.period_start,p.period_end,tp.topology_asset_type_code,tp.topology_asset_id,
                  NULL::varchar AS stored_company_id
             FROM public.hidra_custody_measurement_period p
             JOIN public.hidra_custody_transfer_point tp ON tp.id=p.transfer_point_id
            WHERE p.id=?
        """,id);
    }
    @Override public Optional<Scope> scopeForTicket(String id){
        return scopeQuery("""
           SELECT p.id AS period_id,p.agreement_id,p.transfer_point_id,p.status,p.period_start,p.period_end,
                  tp.topology_asset_type_code,tp.topology_asset_id,r.company_id AS stored_company_id
             FROM public.hidra_custody_t21_ticket t
             JOIN public.hidra_custody_t21_reconciliation r ON r.id=t.reconciliation_id
             JOIN public.hidra_custody_measurement_period p ON p.id=r.period_id
             JOIN public.hidra_custody_transfer_point tp ON tp.id=p.transfer_point_id
            WHERE t.id=?
        """,id);
    }
    @Override public Optional<Scope> scopeForReconciliation(String id){
        return scopeQuery("""
           SELECT p.id AS period_id,p.agreement_id,p.transfer_point_id,p.status,p.period_start,p.period_end,
                  tp.topology_asset_type_code,tp.topology_asset_id,r.company_id AS stored_company_id
             FROM public.hidra_custody_t21_reconciliation r
             JOIN public.hidra_custody_measurement_period p ON p.id=r.period_id
             JOIN public.hidra_custody_transfer_point tp ON tp.id=p.transfer_point_id
            WHERE r.id=?
        """,id);
    }
    @Override public Optional<Scope> scopeForDiscrepancy(String id){
        return scopeQuery("""
           SELECT p.id AS period_id,p.agreement_id,p.transfer_point_id,p.status,p.period_start,p.period_end,
                  tp.topology_asset_type_code,tp.topology_asset_id,r.company_id AS stored_company_id
             FROM public.hidra_custody_t21_discrepancy d
             JOIN public.hidra_custody_t21_reconciliation r ON r.id=d.reconciliation_id
             JOIN public.hidra_custody_measurement_period p ON p.id=r.period_id
             JOIN public.hidra_custody_transfer_point tp ON tp.id=p.transfer_point_id
            WHERE d.id=?
        """,id);
    }
    @Override public void lockPeriod(String id){
        var found=jdbc.query("""
          SELECT id FROM public.hidra_custody_measurement_period WHERE id=? FOR UPDATE
        """,(row,n)->row.getString(1),id);
        if(found.size()!=1)throw new NoSuchElementException("Custody period unavailable.");
    }
    @Override public List<Receipt> receipts(String periodId){
        return jdbc.query("""
          SELECT r.* FROM public.hidra_custody_measurement_receipt r
           WHERE r.period_id=? ORDER BY r.interval_start,r.interval_end,r.measurement_id
        """,(row,n)->new Receipt(row.getString("measurement_id"),row.getString("period_id"),
            row.getString("company_id"),row.getString("transfer_point_id"),row.getString("meter_run_snapshot_id"),
            row.getObject("product_publication_id",UUID.class).toString(),row.getString("agreement_party_id"),
            row.getString("measurement_type_id"),row.getString("unit_id"),row.getString("quantity_basis"),
            time(row,"interval_start"),time(row,"interval_end"),row.getBigDecimal("observed_quantity"),
            row.getString("interval_evidence_id")),periodId);
    }
    private Reconciliation mapReconciliation(ResultSet r)throws SQLException{
        String id=r.getString("id");
        var ids=jdbc.query("""
            SELECT measurement_id FROM public.hidra_custody_t21_reconciliation_receipt
             WHERE reconciliation_id=? ORDER BY ordinal
        """,(row,n)->row.getString(1),id);
        var snapshot=new CustodyReconciliationSnapshot(r.getString("period_id"),r.getString("agreement_id"),
            r.getString("transfer_point_id"),r.getString("company_id"),r.getString("unit_id"),
            "OBSERVED",ids,r.getBigDecimal("observed_total"),r.getBigDecimal("ticket_total"),
            r.getBigDecimal("difference_quantity"),r.getString("selection_version"),
            r.getString("actor_id"),time(r,"created_at"));
        return new Reconciliation(id,r.getString("digest"),r.getString("ticket_id"),r.getString("parent_ticket_id"),
            r.getString("meter_run_snapshot_id"),r.getObject("product_publication_id",UUID.class).toString(),
            r.getString("agreement_party_id"),snapshot);
    }
    @Override public Optional<Reconciliation> reconciliation(String id){
        var rows=jdbc.query("SELECT * FROM "+REC+" WHERE id=?",(row,n)->mapReconciliation(row),id);
        return rows.size()==1?Optional.of(rows.getFirst()):Optional.empty();
    }
    @Override public List<Reconciliation> reconciliations(String periodId){
        return jdbc.query("SELECT * FROM "+REC+" WHERE period_id=? ORDER BY created_at,id",
            (row,n)->mapReconciliation(row),periodId);
    }
    private Ticket mapTicket(ResultSet r)throws SQLException{
        return new Ticket(r.getString("id"),r.getString("reconciliation_id"),r.getString("period_id"),
            r.getString("agreement_id"),r.getString("company_id"),r.getString("transfer_point_id"),
            r.getString("calculation_id"),r.getString("approval_target_digest"),r.getString("issued_by"),
            r.getString("parent_ticket_id"),r.getLong("version"),r.getBigDecimal("ticket_quantity"),
            r.getString("unit_id"),time(r,"issued_at"),time(r,"finalized_at"),
            r.getString("reviewer_id"),r.getString("successor_id"),time(r,"successor_finalized_at")!=null);
    }
    private static final String TICKET_QUERY="""
         SELECT t.*, f.finalized_at,f.reviewer_id,c.successor_ticket_id AS successor_id, sf.finalized_at AS successor_finalized_at
           FROM public.hidra_custody_t21_ticket t
           LEFT JOIN public.hidra_custody_t21_finalization f ON f.ticket_id=t.id
           LEFT JOIN public.hidra_custody_t21_correction c ON c.original_ticket_id=t.id
           LEFT JOIN public.hidra_custody_t21_finalization sf ON sf.ticket_id=c.successor_ticket_id
        """;
    @Override public Optional<Ticket> ticket(String id){
        var rows=jdbc.query(TICKET_QUERY+" WHERE t.id=?",(row,n)->mapTicket(row),id);
        return rows.size()==1?Optional.of(rows.getFirst()):Optional.empty();
    }
    @Override public List<Ticket> history(String ticketId){
        return jdbc.query(TICKET_QUERY+"""
          WHERE t.period_id=(SELECT period_id FROM public.hidra_custody_t21_ticket WHERE id=?)
          ORDER BY t.version,t.id
        """,(row,n)->mapTicket(row),ticketId);
    }
    private Discrepancy mapDiscrepancy(ResultSet r)throws SQLException{
        return new Discrepancy(r.getString("id"),r.getString("reconciliation_id"),r.getString("ticket_id"),
          r.getBigDecimal("difference_quantity"),r.getString("unit_id"),r.getString("disposition"),
          r.getString("reason"),r.getString("evidence_reference"),r.getString("disposed_by"),
          time(r,"opened_at"),time(r,"disposed_at"));
    }
    private static final String DISCREPANCY_QUERY="""
       SELECT d.*,v.disposition,v.reason,v.evidence_reference,v.actor_id AS disposed_by,v.disposed_at
         FROM public.hidra_custody_t21_discrepancy d
         LEFT JOIN public.hidra_custody_t21_disposition v ON v.discrepancy_id=d.id
       """;
    @Override public Optional<Discrepancy> discrepancy(String id){
        var rows=jdbc.query(DISCREPANCY_QUERY+" WHERE d.id=?",(row,n)->mapDiscrepancy(row),id);
        return rows.size()==1?Optional.of(rows.getFirst()):Optional.empty();
    }
    @Override public List<Discrepancy> discrepancies(String reconciliationId){
        return jdbc.query(DISCREPANCY_QUERY+" WHERE d.reconciliation_id=? ORDER BY d.id",
            (row,n)->mapDiscrepancy(row),reconciliationId);
    }
    @Override public Optional<Stored> command(String operation,UUID commandId){
        var rows=jdbc.query("""
            SELECT operation,command_id,actor_id,payload_fingerprint,period_id,resource_id
              FROM public.hidra_custody_t21_command WHERE operation=? AND command_id=?
        """,(row,n)->new Stored(row.getString(1),row.getObject(2,UUID.class),
            row.getString(3),row.getString(4),row.getString(5),row.getString(6)),operation,commandId);
        return rows.size()==1?Optional.of(rows.getFirst()):Optional.empty();
    }
    private void command(Stored c){
        jdbc.update("""
          INSERT INTO public.hidra_custody_t21_command
          (operation,command_id,actor_id,payload_fingerprint,period_id,resource_id)
          VALUES (?,?,?,?,?,?)
        """,c.operation(),c.commandId(),c.actorId(),c.fingerprint(),c.periodId(),c.resourceId());
    }
    private void insertReconciliationEvidence(Reconciliation rec,Ticket ticket,Discrepancy issue){
        var s=rec.snapshot();
        jdbc.update("""
          INSERT INTO public.hidra_custody_t21_reconciliation
          (id,period_id,agreement_id,transfer_point_id,company_id,meter_run_snapshot_id,
           product_publication_id,agreement_party_id,unit_id,digest,ticket_id,parent_ticket_id,
           observed_total,ticket_total,difference_quantity,selection_version,actor_id,created_at)
          VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        """,rec.id(),s.periodId(),s.agreementId(),s.transferPointId(),s.companyId(),rec.meterRunId(),
          UUID.fromString(rec.productPublicationId()),rec.agreementPartyId(),s.unitId(),rec.digest(),
          rec.ticketId(),rec.parentTicketId(),s.observedTotal(),s.ticketTotal(),s.difference(),
          s.selectionVersion(),s.actorId(),Timestamp.from(s.reconciledAt()));
        int ordinal=0;
        for(String receiptId:s.receiptIds()) {
            jdbc.update("""
              INSERT INTO public.hidra_custody_t21_reconciliation_receipt
                (reconciliation_id,ordinal,measurement_id) VALUES (?,?,?)
            """,rec.id(),++ordinal,receiptId);
        }
        jdbc.update("""
          INSERT INTO public.hidra_custody_t21_ticket
          (id,reconciliation_id,period_id,agreement_id,transfer_point_id,company_id,
           calculation_id,approval_target_digest,issued_by,parent_ticket_id,version,
           ticket_quantity,unit_id,issued_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        """,ticket.id(),ticket.reconciliationId(),ticket.periodId(),ticket.agreementId(),
          ticket.transferPointId(),ticket.companyId(),ticket.calculationId(),ticket.approvalTargetDigest(),
          ticket.issuedBy(),ticket.parentTicketId(),ticket.version(),ticket.quantity(),ticket.unitId(),
          Timestamp.from(ticket.issuedAt()));
        jdbc.update("""
          INSERT INTO public.hidra_custody_t21_calculation
          (id,ticket_id,reconciliation_id,observed_total,ticket_total,unit_id,basis,method_version,official,created_at)
          VALUES (?,?,?,?,?,?,'OBSERVED','T21-OBSERVED-1',false,?)
        """,ticket.calculationId(),ticket.id(),rec.id(),s.observedTotal(),s.ticketTotal(),
          s.unitId(),Timestamp.from(s.reconciledAt()));
        if(issue!=null) {
            jdbc.update("""
              INSERT INTO public.hidra_custody_t21_discrepancy
              (id,reconciliation_id,ticket_id,difference_quantity,unit_id,opened_at)
              VALUES (?,?,?,?,?,?)
            """,issue.id(),issue.reconciliationId(),issue.ticketId(),issue.difference(),issue.unitId(),
               Timestamp.from(issue.openedAt()));
        }
    }
    @Override public void insertReconciliation(Reconciliation rec,Ticket ticket,Discrepancy issue,Stored c){
        insertReconciliationEvidence(rec,ticket,issue);command(c);
    }
    @Override public void insertDisposition(String id,String disposition,String reason,
        String evidence,String actor,Instant at,Stored c){
        jdbc.update("""
          INSERT INTO public.hidra_custody_t21_disposition
             (discrepancy_id,disposition,reason,evidence_reference,actor_id,disposed_at)
          VALUES (?,?,?,?,?,?)
        """,id,disposition,reason,evidence,actor,Timestamp.from(at));
        command(c);
    }
    @Override public void insertFinalization(String id,Decision approval,Instant at,Stored c){
        jdbc.update("""
          INSERT INTO public.hidra_custody_t21_finalization
            (ticket_id,workflow_instance_id,workflow_task_id,workflow_action_id,reviewer_id,
             approved_at,finalized_at,finalized_by)
          VALUES (?,?,?,?,?,?,?,?)
        """,id,approval.instanceId(),approval.taskId(),approval.actionId(),approval.reviewerId(),
          Timestamp.from(approval.actedAt()),Timestamp.from(at),c.actorId());
        command(c);
    }
    @Override public void insertCorrection(String originalId,String reason,String evidence,
            Reconciliation rec,Ticket ticket,Discrepancy discrepancy,Stored c){
        insertReconciliationEvidence(rec,ticket,discrepancy);
        jdbc.update("""
          INSERT INTO public.hidra_custody_t21_correction
             (original_ticket_id,successor_ticket_id,reason,evidence_reference,actor_id,corrected_at)
          VALUES (?,?,?,?,?,?)
        """,originalId,ticket.id(),reason,evidence,c.actorId(),Timestamp.from(ticket.issuedAt()));
        command(c);
    }
    // Workflow starts are never authorized by a caller-supplied digest alone.
    @Override public String module(){return "custody";}
    @Override public Set<String> targetTypeCodes(){return Set.of("CUSTODY_TRANSFER_TICKET");}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Target> eligibleTarget(String typeCode,String targetDigest){
        if(!targetTypeCodes().contains(typeCode)||targetDigest==null
           ||!targetDigest.matches("[0-9a-f]{64}"))return Optional.empty();
        var rows=jdbc.query("""
            SELECT t.id,t.company_id,t.transfer_point_id,tp.topology_asset_type_code,tp.topology_asset_id
              FROM public.hidra_custody_t21_ticket t
              JOIN public.hidra_custody_transfer_point tp ON tp.id=t.transfer_point_id
              LEFT JOIN public.hidra_custody_t21_finalization f ON f.ticket_id=t.id
             WHERE t.approval_target_digest=? AND f.ticket_id IS NULL
        """,(row,n)->new String[]{row.getString(1),row.getString(2),row.getString(3),
                 row.getString(4),row.getString(5)},targetDigest);
        if(rows.size()!=1)return Optional.empty();
        String[] t=rows.getFirst();
        String currentOwner=ownership.activeOwner(t[3],t[4]).orElse(null);
        if(!t[1].equals(currentOwner))return Optional.empty();
        try{identity.require("custody:tickets:finalize",t[1],t[2]);}
        catch(NoSuchElementException denied){return Optional.empty();}
        return Optional.of(new Target(targetDigest,t[0],"Custody software ticket "+t[0]));
    }
}
