/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferTicketApprovalEvidenceQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 * @Description : Enforces closed custody ticket approval evidence and independent reviewer policy.
 */
package dz.sh.hidra.modules.workflow.application.service;
import dz.sh.hidra.modules.workflow.application.contract.custody.CustodyTransferTicketApprovalEvidenceContract;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CustodyTransferTicketApprovalEvidenceQueryServiceTest {
    @Test void nullUnknownAndIncorrectDigestNeverReturnApproval(){
        var instances=mock(WorkflowInstanceRepositoryPort.class);
        var tasks=mock(WorkflowTaskRepositoryPort.class);
        var actions=mock(WorkflowActionRepositoryPort.class);
        var config=mock(WorkflowConfigurationPort.class);
        var fence=mock(RevisionApprovalEvidenceQueryService.ManufacturerWorkflowFencePort.class);
        var transitions=mock(WorkflowTransitionApplicationService.class);
        var owner=new CustodyTransferTicketApprovalEvidenceQueryService(
            instances,tasks,actions,config,fence,transitions);
        assertTrue(owner.resolve(null).isEmpty());
        var invalid=new CustodyTransferTicketApprovalEvidenceContract.Request(
            "ticket","invalid","digest","issuer","reconciler","instance","task","action",Instant.now());
        assertTrue(owner.resolve(invalid).isEmpty());
        verifyNoInteractions(fence,instances,tasks,actions,transitions);
        var notFound=new CustodyTransferTicketApprovalEvidenceContract.Request(
            "ticket","a".repeat(64),"b".repeat(64),"issuer","reconciler",
            "instance","task","action",Instant.now());
        assertTrue(owner.resolve(notFound).isEmpty());
        verify(fence).lockCurrentManufacturerApprovalEvidence();
        verify(instances).findById("instance");
        verify(tasks).findById("task");
        verify(actions).findById("action");
    }
}
