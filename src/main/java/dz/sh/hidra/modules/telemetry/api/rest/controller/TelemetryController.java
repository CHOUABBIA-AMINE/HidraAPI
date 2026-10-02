/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : Framework-neutral telemetry controller contract.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.controller;
import dz.sh.hidra.modules.telemetry.api.rest.request.*;
import dz.sh.hidra.modules.telemetry.api.rest.response.*;

/**
 * Framework-neutral telemetry controller contract.
 */
public interface TelemetryController {
    TelemetrySourceResponse createTelemetrySource(CreateTelemetrySourceRequest request);
    TelemetryPointResponse registerTelemetryPoint(RegisterTelemetryPointRequest request);
}
