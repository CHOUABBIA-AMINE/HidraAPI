/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningTelemetryPointReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Resolves canonical Telemetry point snapshots through its own repository port.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.contract.planning.PlanningTelemetryPointReferenceContract;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class PlanningTelemetryPointReferenceQueryService implements PlanningTelemetryPointReferenceContract {
    private final TelemetryPointRepositoryPort points;
    public PlanningTelemetryPointReferenceQueryService(TelemetryPointRepositoryPort points) {
        this.points = Objects.requireNonNull(points);
    }
    @Override
    public Optional<Point> resolve(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        String key = id.trim();
        return points.findById(key).filter(point -> key.equals(point.id()))
                .map(point -> new Point(point.id(), point.code()));
    }
}
