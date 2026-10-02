/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.dto
 *
 * @Description : HSE case summary DTO.
 *
 */
package dz.sh.hidra.modules.hse.application.dto;

import dz.sh.hidra.modules.hse.domain.value.HseCaseSourceType;
import dz.sh.hidra.modules.hse.domain.value.HseCaseStatus;

import java.time.Instant;

/**
 * HSE case summary DTO.
 */
public record HseCaseSummaryDto(
        String id,
        String caseNumber,
        String title,
        String caseTypeId,
        String severityId,
        HseCaseStatus status,
        HseCaseSourceType sourceType,
        String incidentReferenceId,
        String targetModule,
        String targetTypeCode,
        String targetId,
        Instant reportedAt,
        Instant closedAt
) {
}
