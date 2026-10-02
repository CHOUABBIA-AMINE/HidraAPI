/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDiscrepancyResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.response
 *
 * @Description : REST response for custody discrepancy.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.response;

import dz.sh.hidra.modules.custody.domain.value.CustodyDiscrepancyStatus;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * REST response for custody discrepancy.
 */
public record CustodyDiscrepancyResponse(
        String id,
        String discrepancyNumber,
        String reconciliationId,
        String discrepancyTypeId,
        CustodyDiscrepancyStatus status,
        BigDecimal differenceQuantity,
        Instant openedAt
) {
}
