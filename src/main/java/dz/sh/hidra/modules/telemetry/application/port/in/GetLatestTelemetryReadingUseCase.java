/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetLatestTelemetryReadingUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Inbound port for retrieving the latest telemetry reading for a point.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import dz.sh.hidra.modules.telemetry.application.dto.TelemetryReadingDto;
import dz.sh.hidra.modules.telemetry.application.query.GetLatestTelemetryReadingQuery;

/**
 * Inbound port for retrieving the latest telemetry reading for a point.
 *
 * <p>Architecture role:
 * Inbound application port for the telemetry module. It is implemented by application services and
 * called by adapters such as REST controllers or scheduled ingestion entry points.
 */
public interface GetLatestTelemetryReadingUseCase {

    /**
     * Executes the use case.
     *
     * @param query use case input
     * @return use case result
     */
    TelemetryReadingDto getLatestTelemetryReading(GetLatestTelemetryReadingQuery query);
}
