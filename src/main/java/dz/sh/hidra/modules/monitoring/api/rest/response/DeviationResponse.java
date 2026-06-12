/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.response
 *
 * @Description : REST response for monitoring deviation.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.response;

import dz.sh.hidra.modules.monitoring.domain.value.DeviationSeverity;
import dz.sh.hidra.modules.monitoring.domain.value.DeviationStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * REST response for monitoring deviation.
 */
public record DeviationResponse(
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
