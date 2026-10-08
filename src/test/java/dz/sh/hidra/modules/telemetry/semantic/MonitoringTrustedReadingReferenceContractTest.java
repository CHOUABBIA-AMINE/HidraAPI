/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringTrustedReadingReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.semantic
 *
 * @Description : Verifies accepted deviation owner evidence and preserved Monitoring behavior.
 *
 */
package dz.sh.hidra.modules.telemetry.semantic;

import dz.sh.hidra.modules.telemetry.application.port.out.TrustedTelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.service.MonitoringTrustedReadingReferenceQueryService;
import dz.sh.hidra.modules.telemetry.domain.model.TrustedTelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.value.TrustLevel;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonitoringTrustedReadingReferenceContractTest {
    @Test void ownerResolvesRealPointAndGovernedTrustWithoutInventedHighTrustThreshold() {
        var port=mock(TrustedTelemetryReadingRepositoryPort.class);var reading=mock(TrustedTelemetryReading.class);
        when(reading.id()).thenReturn("reading");when(reading.pointId()).thenReturn("point");when(reading.trustLevel()).thenReturn(TrustLevel.LOW);
        when(port.findById("reading")).thenReturn(Optional.of(reading));
        var provider=new MonitoringTrustedReadingReferenceQueryService(port);
        var result=provider.resolve(" reading ").orElseThrow();assertEquals("point",result.pointId());assertEquals("LOW",result.trustLevel());
        assertTrue(provider.resolve("missing").isEmpty());assertTrue(provider.resolve(null).isEmpty());
        when(port.findById("wrong")).thenReturn(Optional.of(reading));assertTrue(provider.resolve("wrong").isEmpty());
    }
}
