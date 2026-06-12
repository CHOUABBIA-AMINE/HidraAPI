/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateLeakCandidateRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.request
 *
 * @Description : REST request to create leak candidate.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.request;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * REST request to create leak candidate.
 */
public record CreateLeakCandidateRequest(
        String runId,
        String profileId,
        String candidateNumber,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        Instant suspectedAt,
        Instant firstEvidenceAt,
        BigDecimal confidenceScore,
        String summary,
        String correlationId
) {
}
