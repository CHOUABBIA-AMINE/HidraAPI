/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCandidateView
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.dto
 *
 * @Description : Read model for leak candidate queries.
 */
package dz.sh.hidra.modules.leakdetection.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record LeakCandidateView(
        String id,
        String runId,
        String profileId,
        String candidateNumber,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetName,
        Instant suspectedAt,
        Instant firstEvidenceAt,
        BigDecimal confidenceScore,
        String severityLevel,
        String status,
        String summary,
        String correlationId,
        Instant createdAt,
        Instant updatedAt
) {
}
