/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenCustodyDiscrepancyRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.request
 *
 * @Description : REST request for open custody discrepancy.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.request;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * REST request for open custody discrepancy.
 */
public record OpenCustodyDiscrepancyRequest(
        String discrepancyNumber,
        String reconciliationId,
        String discrepancyTypeId,
        BigDecimal differenceQuantity,
        String quantityUnitId,
        String description,
        String assignedActorId,
        Instant openedAt
) {
}
