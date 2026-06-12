/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response for telemetry point.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLifecycleStatus;

/**
 * REST response for telemetry point.
 */
public record TelemetryPointResponse(
        String id,
        String deviceId,
        String code,
        String nameFr,
        String signalTypeId,
        String unitId,
        TelemetryLifecycleStatus status
) {
}
