/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyBatch
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Batch transferred under custody.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Batch transferred under custody.
     *
         * @param id id
     * @param batchNumber batchNumber
     * @param measurementPeriodId measurementPeriodId
     * @param agreementId agreementId
     * @param productTypeId productTypeId
     * @param status status
     * @param batchStart batchStart
     * @param batchEnd batchEnd
     * @param expectedQuantity expectedQuantity
     * @param expectedQuantityUnitId expectedQuantityUnitId
     * @param sourcePlanTargetId sourcePlanTargetId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record CustodyBatch(
            String id,
        String batchNumber,
        String measurementPeriodId,
        String agreementId,
        String productTypeId,
        CustodyBatchStatus status,
        Instant batchStart,
        Instant batchEnd,
        BigDecimal expectedQuantity,
        String expectedQuantityUnitId,
        String sourcePlanTargetId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public CustodyBatch {
        id = normalize(id);
        batchNumber = normalize(batchNumber);
        measurementPeriodId = normalize(measurementPeriodId);
        agreementId = normalize(agreementId);
        productTypeId = normalize(productTypeId);
        expectedQuantityUnitId = normalize(expectedQuantityUnitId);
        sourcePlanTargetId = normalize(sourcePlanTargetId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
