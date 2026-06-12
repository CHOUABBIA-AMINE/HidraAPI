/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Repository port for TelemetryPoint.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;

import java.util.Optional;

/**
 * Repository port for TelemetryPoint.
 */
public interface TelemetryPointRepositoryPort {

    TelemetryPoint save(TelemetryPoint model);

    Optional<TelemetryPoint> findById(String id);
}
