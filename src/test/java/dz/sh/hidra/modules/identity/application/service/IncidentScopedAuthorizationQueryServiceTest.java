/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentScopedAuthorizationQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 * @Description : Independent Incident reviewer grant stays within exact current owner authorization.
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class IncidentScopedAuthorizationQueryServiceTest {
    final TelemetryNativeLocalActorPort actors=mock(TelemetryNativeLocalActorPort.class);
    final IncidentExplicitGrantEvidencePort direct=mock(IncidentExplicitGrantEvidencePort.class);
    final AuthorizationEvidencePort evidence=mock(AuthorizationEvidencePort.class);
    final AuthorizationAssertionPort assertions=mock(AuthorizationAssertionPort.class);
    final IncidentScopedAuthorizationQueryService subject=
            new IncidentScopedAuthorizationQueryService(actors,direct,evidence,assertions);
    @Test void reviewPermissionIsAcceptedButOnlyExactCurrentScopedEvidenceCanAuthorizeIt() {
        when(actors.currentLocalActor()).thenReturn("eligible-reviewer");
        assertFalse(subject.permitted("incident:incidents:review","c1","FACILITY","a1"));
        verify(direct).currentDirectPermit(eq("eligible-reviewer"),eq("incident:incidents:review"),
                eq(ScopeType.CUSTOM),eq("incident-company:c1"),any(Instant.class));
        verify(direct,never()).currentDirectPermit(anyString(),eq("incident:incidents:execute"),any(),any(),any());
    }
    @Test void missingCompanyOrAssetAndUnregisteredPermissionFailBeforeOwnerDisclosure() {
        assertFalse(subject.permitted("incident:incidents:review",null,"FACILITY","asset"));
        assertFalse(subject.permitted("incident:incidents:review","c1","PIPELINE","asset"));
        assertFalse(subject.permitted("incident:incidents:admin","c1","FACILITY","asset"));
        verifyNoInteractions(actors,direct,evidence,assertions);
    }
}
