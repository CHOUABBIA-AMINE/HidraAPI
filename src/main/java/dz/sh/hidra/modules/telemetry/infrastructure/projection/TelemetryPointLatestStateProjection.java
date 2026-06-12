/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointLatestStateProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.projection
 *
 * @Description : Latest telemetry point state projection.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.projection;

import dz.sh.hidra.modules.telemetry.domain.value.CommunicationState;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Latest telemetry point state projection.
 */
public record TelemetryPointLatestStateProjection(
        String pointId,
        String lastTrustedReadingId,
        BigDecimal lastNumericValue,
        String lastTextValue,
        Boolean lastBooleanValue,
        CommunicationState communicationState,
        Instant updatedAt
) {
}
