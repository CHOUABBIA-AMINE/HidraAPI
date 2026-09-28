/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDiscrepancy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Discrepancy record.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Discrepancy record.
     *
         * @param id id
     * @param discrepancyNumber discrepancyNumber
     * @param reconciliationId reconciliationId
     * @param discrepancyTypeId discrepancyTypeId
     * @param status status
     * @param differenceQuantity differenceQuantity
     * @param quantityUnitId quantityUnitId
     * @param description description
     * @param rootCauseText rootCauseText
     * @param resolutionText resolutionText
     * @param assignedActorId assignedActorId
     * @param openedAt openedAt
     * @param resolvedAt resolvedAt
     * @param closedAt closedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record CustodyDiscrepancy(
            String id,
        String discrepancyNumber,
        String reconciliationId,
        String discrepancyTypeId,
        CustodyDiscrepancyStatus status,
        BigDecimal differenceQuantity,
        String quantityUnitId,
        String description,
        String rootCauseText,
        String resolutionText,
        String assignedActorId,
        Instant openedAt,
        Instant resolvedAt,
        Instant closedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public CustodyDiscrepancy {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyDiscrepancy id must not be blank.");
        }
        // HRA-051 required: discrepancyNumber
        if (discrepancyNumber == null || discrepancyNumber.isBlank()) {
            throw new InvalidCustodyValueException("CustodyDiscrepancy discrepancy number must not be blank.");
        }
        // HRA-051 required: reconciliationId
        if (reconciliationId == null || reconciliationId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyDiscrepancy reconciliation id must not be blank.");
        }
        // HRA-051 required: discrepancyTypeId
        if (discrepancyTypeId == null || discrepancyTypeId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyDiscrepancy discrepancy type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidCustodyValueException("CustodyDiscrepancy status must not be null.");
        }
        // HRA-051 required: openedAt
        if (openedAt == null) {
            throw new InvalidCustodyValueException("CustodyDiscrepancy opened at must not be null.");
        }
        // HRA-051 order: openedAt <= closedAt
        if (openedAt != null && closedAt != null && closedAt.isBefore(openedAt)) {
            throw new InvalidCustodyValueException("CustodyDiscrepancy closed at must not be before opened at.");
        }

        id = normalize(id);
        discrepancyNumber = normalize(discrepancyNumber);
        reconciliationId = normalize(reconciliationId);
        discrepancyTypeId = normalize(discrepancyTypeId);
        quantityUnitId = normalize(quantityUnitId);
        description = normalize(description);
        rootCauseText = normalize(rootCauseText);
        resolutionText = normalize(resolutionText);
        assignedActorId = normalize(assignedActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
