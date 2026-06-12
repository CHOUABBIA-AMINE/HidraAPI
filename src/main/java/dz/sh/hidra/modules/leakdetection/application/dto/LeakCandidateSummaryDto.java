/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCandidateSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.dto
 *
 * @Description : Leak candidate summary DTO.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.dto;

import dz.sh.hidra.modules.leakdetection.domain.value.LeakCandidateStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakSeverityLevel;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Leak candidate summary DTO.
 */
public record LeakCandidateSummaryDto(
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
