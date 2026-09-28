/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyQuantityCalculation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Official quantity calculation.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Official quantity calculation.
     *
         * @param id id
     * @param calculationNumber calculationNumber
     * @param measurementPeriodId measurementPeriodId
     * @param batchId batchId
     * @param quantityBasis quantityBasis
     * @param grossObservedQuantity grossObservedQuantity
     * @param grossStandardQuantity grossStandardQuantity
     * @param netStandardQuantity netStandardQuantity
     * @param massQuantity massQuantity
     * @param quantityUnitId quantityUnitId
     * @param calculationMethodId calculationMethodId
     * @param calculationDetailsJson calculationDetailsJson
     * @param calculatedByActorId calculatedByActorId
     * @param calculatedAt calculatedAt
     * @param official official
     * @param createdAt createdAt
     */
    public record CustodyQuantityCalculation(
            String id,
        String calculationNumber,
        String measurementPeriodId,
        String batchId,
        CustodyQuantityBasis quantityBasis,
        BigDecimal grossObservedQuantity,
        BigDecimal grossStandardQuantity,
        BigDecimal netStandardQuantity,
        BigDecimal massQuantity,
        String quantityUnitId,
        String calculationMethodId,
        String calculationDetailsJson,
        String calculatedByActorId,
        Instant calculatedAt,
        boolean official,
        Instant createdAt
    ) {

        public CustodyQuantityCalculation {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyQuantityCalculation id must not be blank.");
        }
        // HRA-051 required: calculationNumber
        if (calculationNumber == null || calculationNumber.isBlank()) {
            throw new InvalidCustodyValueException("CustodyQuantityCalculation calculation number must not be blank.");
        }
        // HRA-051 required: measurementPeriodId
        if (measurementPeriodId == null || measurementPeriodId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyQuantityCalculation measurement period id must not be blank.");
        }
        // HRA-051 required: quantityBasis
        if (quantityBasis == null) {
            throw new InvalidCustodyValueException("CustodyQuantityCalculation quantity basis must not be null.");
        }
        // HRA-051 required: quantityUnitId
        if (quantityUnitId == null || quantityUnitId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyQuantityCalculation quantity unit id must not be blank.");
        }
        // HRA-051 required: calculatedAt
        if (calculatedAt == null) {
            throw new InvalidCustodyValueException("CustodyQuantityCalculation calculated at must not be null.");
        }

        id = normalize(id);
        calculationNumber = normalize(calculationNumber);
        measurementPeriodId = normalize(measurementPeriodId);
        batchId = normalize(batchId);
        quantityUnitId = normalize(quantityUnitId);
        calculationMethodId = normalize(calculationMethodId);
        calculationDetailsJson = normalize(calculationDetailsJson);
        calculatedByActorId = normalize(calculatedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
