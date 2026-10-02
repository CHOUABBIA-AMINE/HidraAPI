/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicket
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyTransferTicket id must not be blank.");
        }
        // HRA-051 required: ticketNumber
        if (ticketNumber == null || ticketNumber.isBlank()) {
            throw new InvalidCustodyValueException("CustodyTransferTicket ticket number must not be blank.");
        }
        // HRA-051 required: measurementPeriodId
        if (measurementPeriodId == null || measurementPeriodId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyTransferTicket measurement period id must not be blank.");
        }
        // HRA-051 required: agreementId
        if (agreementId == null || agreementId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyTransferTicket agreement id must not be blank.");
        }
        // HRA-051 required: transferPointId
        if (transferPointId == null || transferPointId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyTransferTicket transfer point id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidCustodyValueException("CustodyTransferTicket status must not be null.");
        }
        // HRA-051 required: ticketDate
        if (ticketDate == null) {
            throw new InvalidCustodyValueException("CustodyTransferTicket ticket date must not be null.");
        }

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
