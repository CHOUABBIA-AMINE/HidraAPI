/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentActorContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import dz.sh.hidra.modules.identity.application.service.IncidentActorQueryService;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import dz.sh.hidra.platform.security.*;
import dz.sh.hidra.kernel.domain.value.ActorId;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncidentActorContractTest {
    final CurrentSecurityContext security=mock(CurrentSecurityContext.class);
    final WorkflowActorContract actors=mock(WorkflowActorContract.class);
    final IncidentActorQueryService query=new IncidentActorQueryService(security,actors);
    @Test void anonymousAndIneligibleActorsFailClosed() {
        assertThatThrownBy(() -> query.currentActor(Instant.now())).isInstanceOf(SecurityException.class);
        when(security.currentPrincipal()).thenReturn(Optional.of(new AuthenticatedPrincipal(new ActorId("actor"),"caller",true)));
        assertThatThrownBy(() -> query.currentActor(Instant.now())).isInstanceOf(SecurityException.class);
    }
    @Test void canonicalIdentitySnapshotsOverridePrincipalName() {
        var at=Instant.now();when(security.currentPrincipal()).thenReturn(Optional.of(new AuthenticatedPrincipal(new ActorId("actor"),"untrusted",true)));
        when(actors.eligibleActor("actor",at)).thenReturn(Optional.of(new WorkflowActorContract.Actor("actor","canonical","Canonical Actor",null)));
        assertThat(query.currentActor(at).displayName()).isEqualTo("Canonical Actor");

    }
}
