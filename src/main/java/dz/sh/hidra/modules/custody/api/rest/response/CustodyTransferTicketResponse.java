/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicketResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.response
 *
 * @Description : REST response for custody transfer ticket.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.response;

import dz.sh.hidra.modules.custody.domain.value.CustodyTicketStatus;

import java.time.Instant;

/**
 * REST response for custody transfer ticket.
 */
public record CustodyTransferTicketResponse(
        String id,
        String ticketNumber,
        String measurementPeriodId,
        String agreementId,
        String transferPointId,
        CustodyTicketStatus status,
        Instant ticketDate,
        Instant approvedAt
) {
}
