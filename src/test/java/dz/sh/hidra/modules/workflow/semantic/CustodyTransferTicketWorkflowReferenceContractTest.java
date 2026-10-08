/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicketWorkflowReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.semantic
 *
 * @Description : Validates owner-controlled CustodyTransferTicket references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.workflow.semantic;

import dz.sh.hidra.modules.workflow.application.service.CustodyTransferTicketWorkflowReferenceQueryService;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CustodyTransferTicketWorkflowReferenceContractTest {
    final WorkflowInstanceRepositoryPort instances=mock(WorkflowInstanceRepositoryPort.class);
    final WorkflowConfigurationPort configuration=mock(WorkflowConfigurationPort.class);
    final WorkflowInstance instance=mock(WorkflowInstance.class);
    final CustodyTransferTicketWorkflowReferenceQueryService service=new CustodyTransferTicketWorkflowReferenceQueryService(instances,configuration);
    void valid() {
        when(instances.findById("workflow")).thenReturn(Optional.of(instance));when(instance.id()).thenReturn("workflow");
        when(instance.targetModule()).thenReturn("custody");when(instance.targetId()).thenReturn("target");when(instance.targetTypeId()).thenReturn("type");
        when(instance.workflowPurposeId()).thenReturn("purpose");when(instance.definitionId()).thenReturn("definition");
        when(configuration.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenReturn(new WorkflowConfigurationPort.Catalog("type","WORKFLOW_TARGET_TYPE","CUSTODY_TRANSFER_TICKET",true));
        when(configuration.requireActiveCatalog("purpose","WORKFLOW_PURPOSE")).thenReturn(new WorkflowConfigurationPort.Catalog("purpose","WORKFLOW_PURPOSE","CONFIGURED_PURPOSE",true));
        when(configuration.activeBinding("definition","custody","type","purpose")).thenReturn(true);
    }
    @Test void validExactContextIsAccepted() {valid();assertTrue(service.matches("workflow","target"));}
    @Test void mereExistenceAndWrongModuleAreInsufficient() {valid();when(instance.targetModule()).thenReturn("other");assertFalse(service.matches("workflow","target"));}
    @Test void differentTargetIsRejected() {valid();assertFalse(service.matches("workflow","other"));}
    @Test void wrongTypeOrInactiveCatalogIsRejected() {valid();when(configuration.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenReturn(new WorkflowConfigurationPort.Catalog("type","WORKFLOW_TARGET_TYPE","OTHER",true));assertFalse(service.matches("workflow","target"));
        when(configuration.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenReturn(new WorkflowConfigurationPort.Catalog("type","WORKFLOW_TARGET_TYPE","CUSTODY_TRANSFER_TICKET",false));assertFalse(service.matches("workflow","target"));}
    @Test void missingBindingIsRejected() {valid();when(configuration.activeBinding("definition","custody","type","purpose")).thenReturn(false);assertFalse(service.matches("workflow","target"));}
    @Test void unavailableConfigurationFailsClosed() {valid();when(configuration.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenThrow(new IllegalStateException("unavailable"));assertThrows(IllegalStateException.class,() -> service.matches("workflow","target"));}
    @Test void missingInstanceIsRejected() {when(instances.findById("missing")).thenReturn(Optional.empty());assertFalse(service.matches("missing","target"));}
}
