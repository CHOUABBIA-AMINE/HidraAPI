/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportWorkflowTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.integration
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.infrastructure.integration;
import dz.sh.hidra.modules.notification.application.contract.reporting.ReportReviewTaskInboxContract;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportOwnerPort;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Phase;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ScopedReportWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    private final ScopedReportRepositoryPort reports;
    private final ScopedReportOwnerPort owners;
    private final ReportReviewTaskInboxContract inbox;
    public ScopedReportWorkflowTargetLookup(ScopedReportRepositoryPort reports, @Lazy ScopedReportOwnerPort owners) {
        this(reports,owners,null);
    }
    @Autowired
    public ScopedReportWorkflowTargetLookup(ScopedReportRepositoryPort reports,
            @Lazy ScopedReportOwnerPort owners,ReportReviewTaskInboxContract inbox) {
        this.reports=reports; this.owners=owners; this.inbox=inbox;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean reportTaskAssigned(String requestId,String taskId,String recipientActorId,
            Instant assignedAt) {
        if (inbox==null || requestId==null || requestId.isBlank()
                || requestId.length()>80 || taskId==null || taskId.isBlank()
                || recipientActorId==null || recipientActorId.isBlank() || assignedAt==null)
            return false;
        var row=reports.workflowTarget(requestId).orElseThrow();
        if (row.phase()!=Phase.SUBMITTED || !requestId.equals(row.requestId()))
            return false;
        String actor=owners.require(row.scope(),ScopedReportOwnerPort.Operation.READ);
        if (!row.actor().equals(actor) || recipientActorId.equals(actor)) return false;
        inbox.assigned(new ReportReviewTaskInboxContract.Assignment(taskId,recipientActorId,
                row.scope().companyId(),row.scope().facilityId(),assignedAt));
        return true;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean scopedReportStarterAllowed(String requestId) {
        return reports.workflowTarget(requestId).filter(row -> {
            try {
                return row.phase()==Phase.SUBMITTED && row.actor().equals(
                        owners.require(row.scope(),ScopedReportOwnerPort.Operation.READ));
            } catch (NoSuchElementException | SecurityException denied) { return false; }
        }).isPresent();
    }
    @Override public String module() { return "reporting"; }
    @Override public Set<String> targetTypeCodes() { return Set.of(REPORT_REQUEST_TARGET); }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Target> eligibleTarget(String type, String id) { return target(type,id,true,false); }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean protectedReviewAllowed(String type,String id,String locator) {
        return target(type,id,true,true).isPresent();
    }
    @Override @Transactional(propagation=Propagation.REQUIRES_NEW)
    public boolean protectedReadAllowed(String type,String id,ProtectedRead read) {
        if (!REPORT_REQUEST_TARGET.equals(type) || id==null || id.isBlank() || id.length()>80)
            return false;
        var row=reports.workflowTarget(id).orElseThrow();
        owners.require(row.scope(),ScopedReportOwnerPort.Operation.READ);
        return true;
    }
    private Optional<Target> target(String type,String id,boolean submitted,boolean reviewer) {
        if (!REPORT_REQUEST_TARGET.equals(type) || id==null || id.isBlank() || id.length()>80)
            return Optional.empty();
        try {
            var row=reports.workflowTarget(id).orElseThrow();
            var actor=owners.require(row.scope(),ScopedReportOwnerPort.Operation.READ);
            if (submitted && row.phase()!=Phase.SUBMITTED || reviewer && row.actor().equals(actor))
                return Optional.empty();
            return Optional.of(new Target(id,id,"Scoped report request"));
        } catch (NoSuchElementException | SecurityException unavailable) {
            return Optional.empty();
        }
    }
}
