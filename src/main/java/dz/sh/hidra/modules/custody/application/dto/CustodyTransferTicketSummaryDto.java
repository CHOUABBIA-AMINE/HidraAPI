/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicketSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.dto
 *
 * @Description : Custody transfer ticket summary DTO.
 *
 */
package dz.sh.hidra.modules.custody.application.dto;

import dz.sh.hidra.modules.custody.domain.value.CustodyTicketStatus;

import java.time.Instant;

/**
 * Custody transfer ticket summary DTO.
 */
public record CustodyTransferTicketSummaryDto(
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
