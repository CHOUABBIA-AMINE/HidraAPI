/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.dto
 *
 * @Description : Plan/actual deviation summary DTO.
 *
 */
package dz.sh.hidra.modules.monitoring.application.dto;

import dz.sh.hidra.modules.monitoring.domain.value.DeviationSeverity;
import dz.sh.hidra.modules.monitoring.domain.value.DeviationStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Plan/actual deviation summary DTO.
 */
public record DeviationSummaryDto(
        String id,
        String planTargetId,
        String topologyAssetType,
        String topologyAssetId,
        BigDecimal actualValue,
        BigDecimal expectedValue,
        BigDecimal differencePercent,
        DeviationSeverity severity,
        DeviationStatus status,
        Instant detectedAt
) {
}
