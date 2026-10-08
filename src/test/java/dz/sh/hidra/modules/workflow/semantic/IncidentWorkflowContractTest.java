/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentWorkflowContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.semantic
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.workflow.semantic;

import dz.sh.hidra.modules.workflow.application.service.IncidentWorkflowQueryService;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IncidentWorkflowContractTest {
    @Test void existenceDoesNotAttestClosureApproval() {
        var query=new IncidentWorkflowQueryService(mock(WorkflowInstanceRepositoryPort.class));
        assertFalse(query.closureApproved("workflow","incident","actor",Instant.now()));
        assertFalse(query.exists(null));
    }
}
