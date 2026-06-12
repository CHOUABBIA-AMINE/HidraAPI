/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterTelemetryPointRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request to register telemetry point.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import java.math.BigDecimal;

/**
 * REST request to register telemetry point.
 */
public record RegisterTelemetryPointRequest(
        String deviceId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String pointTypeId,
        String signalTypeId,
        String unitId,
        Integer samplingPeriodSeconds,
        BigDecimal minOperationalValue,
        BigDecimal maxOperationalValue
) {
}
