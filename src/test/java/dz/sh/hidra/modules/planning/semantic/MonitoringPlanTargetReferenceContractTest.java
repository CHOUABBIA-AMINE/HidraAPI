/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringPlanTargetReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.semantic
 *
 * @Description : Verifies accepted Batch 19 owner evidence and semantic integrity.
 *
 */
package dz.sh.hidra.modules.planning.semantic;

import dz.sh.hidra.modules.planning.application.port.out.PlanTargetRepositoryPort;
import dz.sh.hidra.modules.planning.application.service.MonitoringPlanTargetReferenceQueryService;
import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.domain.value.PlanTargetStatus;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonitoringPlanTargetReferenceContractTest {
    @Test void ownerExportsHistoricalIdentityAndContextWithoutActiveEligibility() {
        var port=mock(PlanTargetRepositoryPort.class);
        var at=Instant.parse("2026-10-08T00:00:00Z");
        var target=new PlanTarget("target","revision",null,null,"type","PIPELINE","asset","CODE",null,
                "point","POINT",null,"STATE",null,null,null,at,at,null,PlanTargetStatus.CANCELLED,at,at);
        when(port.findById("target")).thenReturn(Optional.of(target));
        var provider=new MonitoringPlanTargetReferenceQueryService(port);
        var reference=provider.resolve(" target ").orElseThrow();
        assertEquals("revision",reference.revisionId());assertEquals("point",reference.telemetryPointId());
        assertEquals("CANCELLED",reference.status());assertEquals(at,reference.validTo());
        assertTrue(provider.resolve(null).isEmpty());assertTrue(provider.resolve("missing").isEmpty());
        when(port.findById("other")).thenReturn(Optional.of(target));assertTrue(provider.resolve("other").isEmpty());
    }
}
