/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCandidateResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.response
 *
 * @Description : REST response for leak candidate.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.response;

import dz.sh.hidra.modules.leakdetection.domain.value.LeakCandidateStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakSeverityLevel;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * REST response for leak candidate.
 */
public record LeakCandidateResponse(
        String id,
        String candidateNumber,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        BigDecimal confidenceScore,
        LeakSeverityLevel severityLevel,
        LeakCandidateStatus status,
        Instant suspectedAt
) {
}
