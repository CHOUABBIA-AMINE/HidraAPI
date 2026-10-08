/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringTrustedReadingReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Resolves governed trusted readings without adding new trust eligibility rules.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTrustedReadingReferenceContract;
import dz.sh.hidra.modules.telemetry.application.port.out.TrustedTelemetryReadingRepositoryPort;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class MonitoringTrustedReadingReferenceQueryService implements MonitoringTrustedReadingReferenceContract {
    private final TrustedTelemetryReadingRepositoryPort readings;
    public MonitoringTrustedReadingReferenceQueryService(TrustedTelemetryReadingRepositoryPort readings) {
        this.readings = Objects.requireNonNull(readings);
    }
    @Override
    public Optional<Reading> resolve(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        String key = id.trim();
        return readings.findById(key).filter(r -> key.equals(r.id()))
                .map(r -> new Reading(r.id(), r.pointId(), r.trustLevel().name()));
    }
}
