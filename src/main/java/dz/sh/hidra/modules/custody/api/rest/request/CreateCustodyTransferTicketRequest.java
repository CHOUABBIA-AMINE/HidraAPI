/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateCustodyTransferTicketRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.request
 *
 * @Description : REST request to create custody transfer ticket.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.request;

import java.time.Instant;

/**
 * REST request to create custody transfer ticket.
 */
public record CreateCustodyTransferTicketRequest(
        String ticketNumber,
        String measurementPeriodId,
        String agreementId,
        String transferPointId,
        String batchId,
        String quantityCalculationId,
        Instant ticketDate,
        String issuedByActorId,
        String workflowInstanceId
) {
}
