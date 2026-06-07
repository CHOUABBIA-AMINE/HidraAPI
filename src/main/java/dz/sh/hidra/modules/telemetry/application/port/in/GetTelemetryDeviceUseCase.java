/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetTelemetryDeviceUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Inbound port for retrieving one telemetry device.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import dz.sh.hidra.modules.telemetry.application.dto.TelemetryDeviceDto;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryDeviceByIdQuery;

/**
 * Inbound port for retrieving one telemetry device.
 *
 * <p>Architecture role:
 * Inbound application port for the telemetry module. It is implemented by application services and
 * called by adapters such as REST controllers or scheduled ingestion entry points.
 */
public interface GetTelemetryDeviceUseCase {

    /**
     * Executes the use case.
     *
     * @param query use case input
     * @return use case result
     */
    TelemetryDeviceDto getTelemetryDevice(GetTelemetryDeviceByIdQuery query);
}
