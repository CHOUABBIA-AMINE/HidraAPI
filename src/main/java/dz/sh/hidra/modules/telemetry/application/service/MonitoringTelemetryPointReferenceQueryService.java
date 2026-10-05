/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringTelemetryPointReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Resolves TelemetryPoint identities for Monitoring.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTelemetryPointReferenceContract;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class MonitoringTelemetryPointReferenceQueryService
        implements MonitoringTelemetryPointReferenceContract {

    private final TelemetryPointRepositoryPort telemetryPointRepositoryPort;

    public MonitoringTelemetryPointReferenceQueryService(
            TelemetryPointRepositoryPort telemetryPointRepositoryPort
    ) {
        this.telemetryPointRepositoryPort = Objects.requireNonNull(
                telemetryPointRepositoryPort,
                "TelemetryPointRepositoryPort must not be null."
        );
    }

    @Override
    public boolean exists(String telemetryPointId) {
        if (telemetryPointId == null || telemetryPointId.isBlank()) {
            return false;
        }
        return telemetryPointRepositoryPort.findById(telemetryPointId.trim()).isPresent();
    }
}
