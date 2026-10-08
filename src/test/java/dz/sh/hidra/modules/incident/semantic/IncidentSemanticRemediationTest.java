/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentSemanticRemediationTest
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

import dz.sh.hidra.modules.incident.domain.model.Incident;
import dz.sh.hidra.modules.incident.domain.value.*;
import dz.sh.hidra.modules.incident.infrastructure.integration.*;
import dz.sh.hidra.modules.identity.application.contract.incident.IncidentActorContract;
import dz.sh.hidra.modules.organization.application.contract.incident.IncidentOrganizationContract;
import dz.sh.hidra.modules.topology.application.contract.incident.IncidentTopologyContract;
import dz.sh.hidra.modules.workflow.application.contract.incident.IncidentWorkflowContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class IncidentSemanticRemediationTest {
    public static final Instant AT=Instant.parse("2026-10-08T10:00:00Z");
    public static Incident incident(IncidentStatus status,Instant detected,Instant resolved,Instant closed,String owner,String snapshot) {
        return new Incident("incident","INC-001","Incident",null,"classification","severity",null,status,IncidentSourceType.MANUAL,
            null,null,detected,AT,null,null,null,null,null,null,null,null,null,null,null,null,owner,snapshot,null,0,null,
            resolved,closed,null,"actor","Canonical Actor",AT,AT);
    }
    @Test void rejectsDetectionAfterReporting() {assertThrows(dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException.class,() -> incident(IncidentStatus.OPEN,AT.plusSeconds(1),null,null,null,null));}
    @Test void enforcesResolvedAndClosedCoupling() {
        assertThrows(dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException.class,() -> incident(IncidentStatus.OPEN,AT,AT,null,null,null));
        assertThrows(dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException.class,() -> incident(IncidentStatus.RESOLVED,AT,AT,AT,"actor","Actor"));
    }
    @Test void closedRequiresAnOwnerIdentityAndSnapshot() {
        assertThrows(dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException.class,() -> incident(IncidentStatus.CLOSED,AT,AT,AT,null,"orphan snapshot"));
        assertThrows(dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException.class,() -> incident(IncidentStatus.CLOSED,AT,AT,AT,"actor"," "));
        assertEquals(IncidentStatus.CLOSED,incident(IncidentStatus.CLOSED,AT,AT,AT,"actor","Canonical Actor").status());
    }
    @Test void canonicalActorRequiredForNewIncidentAndHistoryIsPreserved() {
        var actors=mock(IncidentActorContract.class);var policy=new IncidentReferencePolicyAdapter(actors,mock(IncidentOrganizationContract.class),mock(IncidentTopologyContract.class),mock(IncidentWorkflowContract.class));
        var x=incident(IncidentStatus.OPEN,AT,null,null,null,null);
        when(actors.currentActor(any())).thenReturn(new IncidentActorContract.Actor("other","Other"));
        assertThrows(SecurityException.class,() -> policy.validate(x,null));
        policy.validate(x,x);verifyNoInteractionsAfterHistory(actors);
    }
    private void verifyNoInteractionsAfterHistory(IncidentActorContract actors) {verify(actors,times(1)).currentActor(any());}
    @Test void noopNeverClaimsAuthority() {
        var x=new NoopIncidentExternalReferenceResolver();assertFalse(x.topologyAssetExists("PIPELINE","missing"));assertFalse(x.workflowInstanceExists("missing"));
    }
}
