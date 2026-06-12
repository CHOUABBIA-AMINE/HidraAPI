/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCaseResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.response
 *
 * @Description : REST response for leak case.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.response;

import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionCaseStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakSeverityLevel;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * REST response for leak case.
 */
public record LeakCaseResponse(
        String id,
        String caseNumber,
        String primaryCandidateId,
        String topologyAssetType,
        String topologyAssetId,
        LeakDetectionCaseStatus status,
        LeakSeverityLevel severityLevel,
        BigDecimal confidenceScore,
        Instant openedAt,
        Instant closedAt
) {
}
