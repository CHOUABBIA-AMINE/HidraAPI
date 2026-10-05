/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringTelemetryPointReferenceQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Telemetry Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Verifies the Telemetry-owned Monitoring point-reference contract.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MonitoringTelemetryPointReferenceQueryServiceTest {

    @Test
    void resolvesPointExistenceThroughTelemetryOwnerRepository() {
        TelemetryPointRepositoryPort repository = mock(TelemetryPointRepositoryPort.class);
        TelemetryPoint point = mock(TelemetryPoint.class);
        when(repository.findById("point-1")).thenReturn(Optional.of(point));
        when(repository.findById("missing")).thenReturn(Optional.empty());

        MonitoringTelemetryPointReferenceQueryService service =
                new MonitoringTelemetryPointReferenceQueryService(repository);

        assertThat(service.exists(" point-1 ")).isTrue();
        assertThat(service.exists("missing")).isFalse();
        assertThat(service.exists(" ")).isFalse();
        assertThat(service.exists(null)).isFalse();
    }
}
