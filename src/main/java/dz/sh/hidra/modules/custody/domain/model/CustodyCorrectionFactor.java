/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyCorrectionFactor
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Correction factor.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Correction factor.
     *
         * @param id id
     * @param quantityCalculationId quantityCalculationId
     * @param factorTypeId factorTypeId
     * @param factorCode factorCode
     * @param factorValue factorValue
     * @param basisDescription basisDescription
     * @param sourceReferenceId sourceReferenceId
     * @param appliedAt appliedAt
     * @param createdAt createdAt
     */
    public record CustodyCorrectionFactor(
            String id,
        String quantityCalculationId,
        String factorTypeId,
        String factorCode,
        BigDecimal factorValue,
        String basisDescription,
        String sourceReferenceId,
        Instant appliedAt,
        Instant createdAt
    ) {

        public CustodyCorrectionFactor {
        id = normalize(id);
        quantityCalculationId = normalize(quantityCalculationId);
        factorTypeId = normalize(factorTypeId);
        factorCode = normalize(factorCode);
        basisDescription = normalize(basisDescription);
        sourceReferenceId = normalize(sourceReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
