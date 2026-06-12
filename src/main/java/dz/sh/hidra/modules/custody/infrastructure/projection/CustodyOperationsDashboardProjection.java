/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyOperationsDashboardProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.projection
 *
 * @Description : Custody operations dashboard projection.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.projection;

import dz.sh.hidra.modules.custody.domain.value.CustodyPeriodStatus;
import dz.sh.hidra.modules.custody.domain.value.CustodyTicketStatus;

import java.time.Instant;

/**
 * Custody operations dashboard projection.
 */
public record CustodyOperationsDashboardProjection(
        String measurementPeriodId,
        String periodCode,
        String transferPointId,
        CustodyPeriodStatus periodStatus,
        int ticketCount,
        CustodyTicketStatus mostRecentTicketStatus,
        Instant periodStart,
        Instant periodEnd
) {
}
