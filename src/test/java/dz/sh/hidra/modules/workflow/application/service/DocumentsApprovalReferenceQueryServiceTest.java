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
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import java.time.Instant;
import java.lang.reflect.Proxy;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DocumentsApprovalReferenceQueryServiceTest {
    @Test void existenceIsOwnerControlledWithoutInventingApprovalLifecycle(){
        Instant at=Instant.parse("2026-10-07T00:00:00Z");
        var instance=new WorkflowInstance("wf","def",1,"purpose","documents","type","version",null,null,WorkflowInstanceStatus.STARTED,null,"actor","user","Actor",null,at,null,null,null,at,at);
        var repo=(WorkflowInstanceRepositoryPort)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{WorkflowInstanceRepositoryPort.class},(p,m,a)->"wf".equals(a[0])?Optional.of(instance):Optional.empty());
        var service=new DocumentsApprovalReferenceQueryService(repo);
        assertTrue(service.exists(" wf "));assertFalse(service.exists("missing"));assertFalse(service.exists(null));
    }
}
