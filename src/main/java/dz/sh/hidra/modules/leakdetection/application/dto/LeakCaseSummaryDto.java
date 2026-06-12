/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCaseSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.dto
 *
 * @Description : Leak case summary DTO.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.dto;

import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionCaseStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakSeverityLevel;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Leak case summary DTO.
 */
public record LeakCaseSummaryDto(
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
