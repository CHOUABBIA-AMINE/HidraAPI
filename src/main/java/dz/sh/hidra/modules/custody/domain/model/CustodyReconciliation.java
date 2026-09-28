/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyReconciliation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Reconciliation record.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Reconciliation record.
     *
         * @param id id
     * @param reconciliationNumber reconciliationNumber
     * @param measurementPeriodId measurementPeriodId
     * @param agreementId agreementId
     * @param status status
     * @param totalTicketQuantity totalTicketQuantity
     * @param totalMeasuredQuantity totalMeasuredQuantity
     * @param differenceQuantity differenceQuantity
     * @param quantityUnitId quantityUnitId
     * @param differencePercent differencePercent
     * @param reconciledByActorId reconciledByActorId
     * @param reconciledAt reconciledAt
     * @param approvedByActorId approvedByActorId
     * @param approvedAt approvedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record CustodyReconciliation(
            String id,
        String reconciliationNumber,
        String measurementPeriodId,
        String agreementId,
        CustodyReconciliationStatus status,
        BigDecimal totalTicketQuantity,
        BigDecimal totalMeasuredQuantity,
        BigDecimal differenceQuantity,
        String quantityUnitId,
        BigDecimal differencePercent,
        String reconciledByActorId,
        Instant reconciledAt,
        String approvedByActorId,
        Instant approvedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public CustodyReconciliation {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyReconciliation id must not be blank.");
        }
        // HRA-051 required: reconciliationNumber
        if (reconciliationNumber == null || reconciliationNumber.isBlank()) {
            throw new InvalidCustodyValueException("CustodyReconciliation reconciliation number must not be blank.");
        }
        // HRA-051 required: measurementPeriodId
        if (measurementPeriodId == null || measurementPeriodId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyReconciliation measurement period id must not be blank.");
        }
        // HRA-051 required: agreementId
        if (agreementId == null || agreementId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyReconciliation agreement id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidCustodyValueException("CustodyReconciliation status must not be null.");
        }

        id = normalize(id);
        reconciliationNumber = normalize(reconciliationNumber);
        measurementPeriodId = normalize(measurementPeriodId);
        agreementId = normalize(agreementId);
        quantityUnitId = normalize(quantityUnitId);
        reconciledByActorId = normalize(reconciledByActorId);
        approvedByActorId = normalize(approvedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
