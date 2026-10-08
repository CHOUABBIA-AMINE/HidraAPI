/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningTelemetryPointReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.semantic
 *
 * @Description : Verifies accepted Batch 19 owner evidence and semantic integrity.
 *
 */
package dz.sh.hidra.modules.telemetry.semantic;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.service.PlanningTelemetryPointReferenceQueryService;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanningTelemetryPointReferenceContractTest {
    @Test void actualOwnerProviderResolvesCodeAndDeniesMissingOrMismatchedIdentity() {
        var port=mock(TelemetryPointRepositoryPort.class);
        var point=mock(TelemetryPoint.class);when(point.id()).thenReturn("point");when(point.code()).thenReturn("OWNER");
        when(port.findById("point")).thenReturn(Optional.of(point));
        var provider=new PlanningTelemetryPointReferenceQueryService(port);
        assertEquals("OWNER",provider.resolve(" point ").orElseThrow().code());
        assertTrue(provider.resolve(null).isEmpty());assertTrue(provider.resolve("missing").isEmpty());
        when(port.findById("wrong")).thenReturn(Optional.of(point));assertTrue(provider.resolve("wrong").isEmpty());
    }
}
