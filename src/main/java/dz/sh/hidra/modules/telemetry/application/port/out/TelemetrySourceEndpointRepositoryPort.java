/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceEndpointRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Repository port for TelemetrySourceEndpoint.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySourceEndpoint;

import java.util.Optional;

/**
 * Repository port for TelemetrySourceEndpoint.
 */
public interface TelemetrySourceEndpointRepositoryPort {

    TelemetrySourceEndpoint save(TelemetrySourceEndpoint model);

    Optional<TelemetrySourceEndpoint> findById(String id);
}
