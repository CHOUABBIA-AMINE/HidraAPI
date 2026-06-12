/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.dto
 *
 * @Description : Telemetry source summary DTO.
 *
 */
package dz.sh.hidra.modules.telemetry.application.dto;

import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLifecycleStatus;

/**
 * Telemetry source summary DTO.
 */
public record TelemetrySourceSummaryDto(
        String id,
        String code,
        String nameFr,
        String sourceTypeId,
        String protocolId,
        TelemetryLifecycleStatus status
) {
}
