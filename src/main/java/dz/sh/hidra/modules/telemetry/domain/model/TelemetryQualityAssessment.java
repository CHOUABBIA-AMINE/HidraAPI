/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQualityAssessment
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Validation/quality assessment result for a reading.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;

    /**
     * Validation/quality assessment result for a reading.
     *
         * @param id id
     * @param readingId readingId
     * @param pointId pointId
     * @param assessmentStatus assessmentStatus
     * @param inputQualityCodeId inputQualityCodeId
     * @param resolvedQualityCodeId resolvedQualityCodeId
     * @param trustLevel trustLevel
     * @param validationRuleId validationRuleId
     * @param reasonCode reasonCode
     * @param reasonMessage reasonMessage
     * @param assessedAt assessedAt
     * @param assessedByActorId assessedByActorId
     * @param workflowInstanceId workflowInstanceId
     */
    public record TelemetryQualityAssessment(
            String id,
        String readingId,
        String pointId,
        AssessmentStatus assessmentStatus,
        String inputQualityCodeId,
        String resolvedQualityCodeId,
        TrustLevel trustLevel,
        String validationRuleId,
        String reasonCode,
        String reasonMessage,
        Instant assessedAt,
        String assessedByActorId,
        String workflowInstanceId
    ) {

        public TelemetryQualityAssessment {
        id = normalize(id);
        readingId = normalize(readingId);
        pointId = normalize(pointId);
        inputQualityCodeId = normalize(inputQualityCodeId);
        resolvedQualityCodeId = normalize(resolvedQualityCodeId);
        validationRuleId = normalize(validationRuleId);
        reasonCode = normalize(reasonCode);
        reasonMessage = normalize(reasonMessage);
        assessedByActorId = normalize(assessedByActorId);
        workflowInstanceId = normalize(workflowInstanceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
