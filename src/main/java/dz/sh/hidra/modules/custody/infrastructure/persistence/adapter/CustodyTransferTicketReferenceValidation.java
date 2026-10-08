/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicketReferenceValidation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Validates owner-controlled CustodyTransferTicket references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.domain.model.CustodyTransferTicket;
import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyTransferTicketActorReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.custody.CustodyTransferTicketWorkflowReferenceContract;
import dz.sh.hidra.modules.audit.application.contract.custody.CustodyTicketAuditReferenceContract;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyBatchJpaRepository;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyQuantityCalculationJpaRepository;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public final class CustodyTransferTicketReferenceValidation {
    private final CustodyTransferTicketActorReferenceContract actors;
    private final CustodyTransferTicketWorkflowReferenceContract workflow;
    private final CustodyTicketAuditReferenceContract audit;
    private final CustodyBatchJpaRepository batchId;
    private final CustodyQuantityCalculationJpaRepository quantityCalculationId;
    public CustodyTransferTicketReferenceValidation(CustodyTransferTicketActorReferenceContract actors, CustodyTransferTicketWorkflowReferenceContract workflow, CustodyTicketAuditReferenceContract audit, CustodyBatchJpaRepository batchId, CustodyQuantityCalculationJpaRepository quantityCalculationId) {
        this.actors=Objects.requireNonNull(actors); this.workflow=Objects.requireNonNull(workflow);
        this.audit=Objects.requireNonNull(audit);
        this.batchId=Objects.requireNonNull(batchId);
        this.quantityCalculationId=Objects.requireNonNull(quantityCalculationId);
    }
    /** Called inside the parent save transaction after locking existing provenance. */
    public void validate(CustodyTransferTicket model,CustodyTransferTicket previous) {
        Objects.requireNonNull(model);
        Instant at=Instant.now();
        if(model.batchId()!=null && !batchId.existsById(model.batchId())) throw new InvalidCustodyValueException("Missing local batchId reference.");
        if(model.quantityCalculationId()!=null && !quantityCalculationId.existsById(model.quantityCalculationId())) throw new InvalidCustodyValueException("Missing local quantityCalculationId reference.");
        if(changed(model.issuedByActorId(), previous==null?null:previous.issuedByActorId()) && !actors.eligible(model.issuedByActorId(),at))
            throw new InvalidCustodyValueException("Ineligible Identity issuedByActorId reference.");
        if(changed(model.approvedByActorId(), previous==null?null:previous.approvedByActorId()) && !actors.eligible(model.approvedByActorId(),at))
            throw new InvalidCustodyValueException("Ineligible Identity approvedByActorId reference.");
        if(changed(model.auditReferenceId(), previous==null?null:previous.auditReferenceId()) && !audit.matches(model.auditReferenceId(),model.id()))
            throw new InvalidCustodyValueException("Audit evidence does not target this Custody ticket.");
        if(changed(model.workflowInstanceId(),previous==null?null:previous.workflowInstanceId()) && !workflow.matches(model.workflowInstanceId(),model.id()))
            throw new InvalidCustodyValueException("Workflow does not target this CustodyTransferTicket context.");
    }
    private static boolean changed(String current,String previous) {return current!=null && !Objects.equals(current,previous);}
}
