/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCaseView
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.dto
 *
 * @Description : Read model for leak case queries.
 */
package dz.sh.hidra.modules.leakdetection.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record LeakCaseView(
        String id,
        String caseNumber,
        String primaryCandidateId,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String owningOrganizationUnitId,
        String status,
        String severityLevel,
        BigDecimal confidenceScore,
        Instant openedAt,
        Instant closedAt,
        String openedByActorId,
        String closedByActorId,
        String closureReasonId,
        String correlationId,
        Instant createdAt,
        Instant updatedAt
) {
}
