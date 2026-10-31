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
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.util.NoSuchElementException;
import org.springframework.security.access.AccessDeniedException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuditWorkflowReferenceQueryServiceTest {
 @Test void onlyOwnerProvenReferencesResolve() {
  var queries=mock(WorkflowQueryUseCase.class);var service=new AuditWorkflowReferenceQueryService(queries);
  when(queries.instance("known")).thenReturn(new WorkflowQueryUseCase.InstanceView(
    "known","def",1,"purpose","hse","type","target",null,null,"STARTED",null,
    "actor","user","Actor",null,null,null,null));
  when(queries.instance("missing")).thenThrow(new NoSuchElementException("Unavailable"));
  when(queries.instance("foreign")).thenThrow(new AccessDeniedException("Unavailable"));
  assertFalse(service.exists(null));assertFalse(service.exists(" "));
  assertFalse(service.exists("missing"));assertFalse(service.exists("foreign"));
  assertTrue(service.exists(" known "));
 }
}
