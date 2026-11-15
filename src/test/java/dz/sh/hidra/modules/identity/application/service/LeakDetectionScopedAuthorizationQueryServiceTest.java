/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakDetectionScopedAuthorizationQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 * @Description : Dedicated Leak case review cannot reuse ordinary suspected-candidate execute grants.
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class LeakDetectionScopedAuthorizationQueryServiceTest {
    final TelemetryNativeLocalActorPort actors=mock(TelemetryNativeLocalActorPort.class);
    final LeakDetectionExplicitGrantEvidencePort direct=mock(LeakDetectionExplicitGrantEvidencePort.class);
    final AuthorizationEvidencePort evidence=mock(AuthorizationEvidencePort.class);
    final AuthorizationAssertionPort assertions=mock(AuthorizationAssertionPort.class);
    final LeakDetectionScopedAuthorizationQueryService subject=
            new LeakDetectionScopedAuthorizationQueryService(actors,direct,evidence,assertions);
    @Test void reviewerHasDistinctCodeAndRequiresCurrentCompanyGrant() {
        when(actors.currentLocalActor()).thenReturn("independent-reviewer");
        assertFalse(subject.permitted("leakdetection:cases:review","c1","FACILITY","a1"));
        verify(direct).currentDirectPermit(eq("independent-reviewer"),eq("leakdetection:cases:review"),
                eq(ScopeType.CUSTOM),eq("leakdetection-company:c1"),any(Instant.class));
        verify(direct,never()).currentDirectPermit(anyString(),eq("leakdetection:candidates:execute"),
                any(),any(),any());
    }
    @Test void noGlobalFallbackForWrongAssetCompanyOrUnknownOperation() {
        assertFalse(subject.permitted("leakdetection:cases:review","c1","PIPELINE","asset"));
        assertFalse(subject.permitted("leakdetection:cases:review","c1","FACILITY",null));
        assertFalse(subject.permitted("leakdetection:cases:approve-all","c1","FACILITY","asset"));
        verifyNoInteractions(actors,direct,evidence,assertions);
    }
}
