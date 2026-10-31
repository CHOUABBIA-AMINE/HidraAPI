/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsApprovalReferenceQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.util.NoSuchElementException;
import org.springframework.security.access.AccessDeniedException;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DocumentsApprovalReferenceQueryServiceTest {
    @Test void existenceIsOwnerControlledWithoutInventingApprovalLifecycle(){
        var queries=mock(WorkflowQueryUseCase.class);
        var service=new DocumentsApprovalReferenceQueryService(queries);
        when(queries.instance("wf")).thenReturn(new WorkflowQueryUseCase.InstanceView(
            "wf","def",1,"purpose","documents","type","version",null,null,"STARTED",null,
            "actor","user","Actor",null,null,null,null));
        when(queries.instance("missing")).thenThrow(new NoSuchElementException("Unavailable"));
        when(queries.instance("foreign")).thenThrow(new AccessDeniedException("Unavailable"));
        assertTrue(service.exists(" wf "));assertFalse(service.exists("missing"));
        assertFalse(service.exists("foreign"));assertFalse(service.exists(null));
    }
}
