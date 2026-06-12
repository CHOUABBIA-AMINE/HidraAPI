/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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

import dz.sh.hidra.modules.telemetry.api.rest.request.CreateTelemetrySourceRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.RegisterTelemetryPointRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPointResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetrySourceResponse;

/**
 * Framework-neutral telemetry controller contract.
 */
public interface TelemetryController {

    TelemetrySourceResponse createTelemetrySource(CreateTelemetrySourceRequest request);

    TelemetryPointResponse registerTelemetryPoint(RegisterTelemetryPointRequest request);
}
