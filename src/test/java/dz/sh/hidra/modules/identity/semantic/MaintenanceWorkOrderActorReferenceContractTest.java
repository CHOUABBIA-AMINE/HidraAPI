/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderActorReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Validates owner-controlled MaintenanceWorkOrder references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import dz.sh.hidra.modules.identity.application.service.MaintenanceWorkOrderActorReferenceQueryService;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class MaintenanceWorkOrderActorReferenceContractTest {
    @Test void missingAndWrongIdentityAreRejected() {
        var owner=mock(WorkflowActorContract.class);var at=Instant.now();var service=new MaintenanceWorkOrderActorReferenceQueryService(owner);
        when(owner.eligibleActor("actor",at)).thenReturn(Optional.empty());assertFalse(service.eligible("actor",at));
        when(owner.eligibleActor("actor",at)).thenReturn(Optional.of(new WorkflowActorContract.Actor("other","name","Name",null)));assertFalse(service.eligible("actor",at));
        assertFalse(service.eligible(" ",at));assertFalse(service.eligible("actor",null));
    }
    @Test void actualEligibleActorIsAccepted() {
        var owner=mock(WorkflowActorContract.class);var at=Instant.now();when(owner.eligibleActor("actor",at)).thenReturn(Optional.of(new WorkflowActorContract.Actor("actor","name","Name",null)));
        assertTrue(new MaintenanceWorkOrderActorReferenceQueryService(owner).eligible(" actor ",at));
    }
}
