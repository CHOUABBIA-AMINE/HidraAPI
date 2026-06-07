/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseTelemetryPointBindingUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Inbound port for closing telemetry point topology bindings.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import dz.sh.hidra.modules.telemetry.application.command.CloseTelemetryPointBindingCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPointBindingDto;

/**
 * Inbound port for closing telemetry point topology bindings.
 *
 * <p>Architecture role:
 * Inbound application port for the telemetry module. It is implemented by application services and
 * called by adapters such as REST controllers or scheduled ingestion entry points.
 */
public interface CloseTelemetryPointBindingUseCase {

    /**
     * Executes the use case.
     *
     * @param command use case input
     * @return use case result
     */
    TelemetryPointBindingDto closeTelemetryPointBinding(CloseTelemetryPointBindingCommand command);
}
