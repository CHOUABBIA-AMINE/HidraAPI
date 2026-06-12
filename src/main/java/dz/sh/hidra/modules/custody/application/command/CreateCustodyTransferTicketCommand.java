/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateCustodyTransferTicketCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.command
 *
 * @Description : Command to create custody transfer ticket.
 *
 */
package dz.sh.hidra.modules.custody.application.command;

import java.time.Instant;

/**
 * Command to create custody transfer ticket.
 */
public record CreateCustodyTransferTicketCommand(
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
