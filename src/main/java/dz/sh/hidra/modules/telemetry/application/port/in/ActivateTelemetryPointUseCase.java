/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ActivateTelemetryPointUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Inbound port for activating telemetry points.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import dz.sh.hidra.modules.telemetry.application.command.ActivateTelemetryPointCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointDto;

/**
 * Inbound port for activating telemetry points.
 *
 * <p>Architecture role:
 * Inbound application port for the telemetry module. It is implemented by application services and
 * called by adapters such as REST controllers or scheduled ingestion entry points.
 */
public interface ActivateTelemetryPointUseCase {

    /**
     * Executes the use case.
     *
     * @param command use case input
     * @return use case result
     */
    TelemetryPointDto activateTelemetryPoint(ActivateTelemetryPointCommand command);
}
