/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyReconciliation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
