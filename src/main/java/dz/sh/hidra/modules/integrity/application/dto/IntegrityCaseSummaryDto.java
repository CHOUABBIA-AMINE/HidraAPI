/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.dto
 *
 * @Description : Integrity case summary DTO.
 *
 */
package dz.sh.hidra.modules.integrity.application.dto;

import dz.sh.hidra.modules.integrity.domain.value.IntegrityCaseStatus;

import java.time.Instant;

/**
 * Integrity case summary DTO.
 */
public record IntegrityCaseSummaryDto(
        String id,
        String caseNumber,
        String title,
        String caseTypeId,
        IntegrityCaseStatus status,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String primaryDefectId,
        Instant openedAt,
        Instant closedAt
) {
}
