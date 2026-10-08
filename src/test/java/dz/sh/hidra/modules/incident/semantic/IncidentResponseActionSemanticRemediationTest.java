/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentResponseActionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.semantic
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.incident.semantic;

import dz.sh.hidra.modules.incident.application.command.RecordIncidentResponseActionCommand;
import dz.sh.hidra.modules.incident.application.service.IncidentApplicationService;
import dz.sh.hidra.modules.incident.application.port.out.*;
import dz.sh.hidra.modules.incident.domain.model.IncidentResponseAction;
import dz.sh.hidra.modules.incident.domain.value.*;
import dz.sh.hidra.modules.incident.domain.exception.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IncidentResponseActionSemanticRemediationTest {
    RecordIncidentResponseActionCommand command(String description) {return new RecordIncidentResponseActionCommand("incident","action",ResponseActionStatus.PLANNED,description,null,null,null,null,null,null,null,null,null,null,null,null);}
    @Test void missingParentCannotCreateResponseAction() {
        var parents=mock(IncidentRepositoryPort.class);var actions=mock(IncidentResponseActionRepositoryPort.class);
        var service=new IncidentApplicationService(parents,actions,mock(IncidentClosureRepositoryPort.class),mock(IncidentReferencePolicyPort.class));
        when(parents.findByIdForUpdate("incident")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,() -> service.recordResponseAction(command("Action")));verifyNoInteractions(actions);
    }
    @Test void closedAndDraftParentCannotReceiveActions() {
        var parents=mock(IncidentRepositoryPort.class);var actions=mock(IncidentResponseActionRepositoryPort.class);
        var service=new IncidentApplicationService(parents,actions,mock(IncidentClosureRepositoryPort.class),mock(IncidentReferencePolicyPort.class));
        var at=IncidentSemanticRemediationTest.AT;
        for(var state:new IncidentStatus[]{IncidentStatus.DRAFT,IncidentStatus.CLOSED,IncidentStatus.CANCELLED,IncidentStatus.MERGED}) {
            when(parents.findByIdForUpdate("incident")).thenReturn(Optional.of(IncidentSemanticRemediationTest.incident(state,at,null,null,"actor","Actor")));
            assertThrows(IncidentLifecycleViolationException.class,() -> service.recordResponseAction(command("Action")));
        }
        verifyNoInteractions(actions);
    }
    @Test void nonblankDescriptionRequiredBeforePersistence() {
        assertThrows(InvalidIncidentValueException.class,() -> new IncidentResponseAction("a","incident","action",ResponseActionStatus.PLANNED," ",null,null,null,null,null,null,null,null,null,null,null,null,IncidentSemanticRemediationTest.AT,IncidentSemanticRemediationTest.AT));
    }
}
