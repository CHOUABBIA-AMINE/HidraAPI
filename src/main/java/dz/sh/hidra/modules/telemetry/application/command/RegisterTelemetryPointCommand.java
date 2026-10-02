/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterTelemetryPointCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.command
 *
 * @Description : Command to register telemetry point.
 *
 */
package dz.sh.hidra.modules.telemetry.application.command;

import java.math.BigDecimal;

/**
 * Command to register telemetry point.
 */
public record RegisterTelemetryPointCommand(
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
