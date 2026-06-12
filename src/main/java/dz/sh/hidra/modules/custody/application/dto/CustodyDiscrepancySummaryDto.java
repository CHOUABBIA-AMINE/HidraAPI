/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDiscrepancySummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.dto
 *
 * @Description : Custody discrepancy summary DTO.
 *
 */
package dz.sh.hidra.modules.custody.application.dto;

import dz.sh.hidra.modules.custody.domain.value.CustodyDiscrepancyStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Custody discrepancy summary DTO.
 */
public record CustodyDiscrepancySummaryDto(
        String id,
        String discrepancyNumber,
        String reconciliationId,
        String discrepancyTypeId,
        CustodyDiscrepancyStatus status,
        BigDecimal differenceQuantity,
        Instant openedAt
) {
}
