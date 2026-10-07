/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditWorkflowReferenceQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Enforces Audit evidence integrity and explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuditWorkflowReferenceQueryServiceTest {
 @Test void onlyOwnerProvenReferencesResolve() {
  var repo=mock(WorkflowInstanceRepositoryPort.class);var service=new AuditWorkflowReferenceQueryService(repo);
  when(repo.findById("known")).thenReturn(Optional.of(mock(WorkflowInstance.class)));
  assertFalse(service.exists(null));assertFalse(service.exists(" "));assertFalse(service.exists("missing"));assertTrue(service.exists(" known "));
 }
}
