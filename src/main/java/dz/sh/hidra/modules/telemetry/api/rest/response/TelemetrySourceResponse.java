/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response for telemetry source.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLifecycleStatus;

/**
 * REST response for telemetry source.
 */
public record TelemetrySourceResponse(
        String id,
        String code,
        String nameFr,
        String sourceTypeId,
        String protocolId,
        TelemetryLifecycleStatus status
) {
}
