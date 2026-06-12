/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicket
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Official transfer ticket.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.value.*;
import java.time.Instant;

    /**
     * Official transfer ticket.
     *
         * @param id id
     * @param ticketNumber ticketNumber
     * @param measurementPeriodId measurementPeriodId
     * @param agreementId agreementId
     * @param transferPointId transferPointId
     * @param batchId batchId
     * @param quantityCalculationId quantityCalculationId
     * @param status status
     * @param ticketDate ticketDate
     * @param issuedByActorId issuedByActorId
     * @param approvedByActorId approvedByActorId
     * @param approvedAt approvedAt
     * @param workflowInstanceId workflowInstanceId
     * @param auditReferenceId auditReferenceId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record CustodyTransferTicket(
            String id,
        String ticketNumber,
        String measurementPeriodId,
        String agreementId,
        String transferPointId,
        String batchId,
        String quantityCalculationId,
        CustodyTicketStatus status,
        Instant ticketDate,
        String issuedByActorId,
        String approvedByActorId,
        Instant approvedAt,
        String workflowInstanceId,
        String auditReferenceId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public CustodyTransferTicket {
        id = normalize(id);
        ticketNumber = normalize(ticketNumber);
        measurementPeriodId = normalize(measurementPeriodId);
        agreementId = normalize(agreementId);
        transferPointId = normalize(transferPointId);
        batchId = normalize(batchId);
        quantityCalculationId = normalize(quantityCalculationId);
        issuedByActorId = normalize(issuedByActorId);
        approvedByActorId = normalize(approvedByActorId);
        workflowInstanceId = normalize(workflowInstanceId);
        auditReferenceId = normalize(auditReferenceId);
        }
        public boolean approvedOrClosed() {
            return status == CustodyTicketStatus.APPROVED
                    || status == CustodyTicketStatus.CLOSED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
