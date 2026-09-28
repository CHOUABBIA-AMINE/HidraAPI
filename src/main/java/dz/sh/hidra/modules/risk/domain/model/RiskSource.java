/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskSource
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Origin that triggered or justified a risk assessment.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import dz.sh.hidra.modules.risk.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Origin that triggered or justified a risk assessment.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param sourceModule sourceModule
     * @param sourceType sourceType
     * @param sourceId sourceId
     * @param sourceCodeSnapshot sourceCodeSnapshot
     * @param sourceLabelSnapshot sourceLabelSnapshot
     * @param sourceObservedAt sourceObservedAt
     * @param sourceSeveritySnapshot sourceSeveritySnapshot
     * @param sourceConfidenceSnapshot sourceConfidenceSnapshot
     * @param createdAt createdAt
     */
    public record RiskSource(
            String id,
        String riskAssessmentId,
        RiskSourceModule sourceModule,
        String sourceType,
        String sourceId,
        String sourceCodeSnapshot,
        String sourceLabelSnapshot,
        Instant sourceObservedAt,
        String sourceSeveritySnapshot,
        BigDecimal sourceConfidenceSnapshot,
        Instant createdAt
    ) {

        public RiskSource {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidRiskValueException("RiskSource id must not be blank.");
        }
        // HRA-051 required: riskAssessmentId
        if (riskAssessmentId == null || riskAssessmentId.isBlank()) {
            throw new InvalidRiskValueException("RiskSource risk assessment id must not be blank.");
        }
        // HRA-051 required: sourceModule
        if (sourceModule == null) {
            throw new InvalidRiskValueException("RiskSource source module must not be null.");
        }
        // HRA-051 required: sourceId
        if (sourceId == null || sourceId.isBlank()) {
            throw new InvalidRiskValueException("RiskSource source id must not be blank.");
        }

        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        sourceType = normalize(sourceType);
        sourceId = normalize(sourceId);
        sourceCodeSnapshot = normalize(sourceCodeSnapshot);
        sourceLabelSnapshot = normalize(sourceLabelSnapshot);
        sourceSeveritySnapshot = normalize(sourceSeveritySnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
