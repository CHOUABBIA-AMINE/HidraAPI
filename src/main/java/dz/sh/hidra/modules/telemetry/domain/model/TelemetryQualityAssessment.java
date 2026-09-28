/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQualityAssessment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryQualityAssessment id must not be blank.");
        }
        // HRA-051 required: readingId
        if (readingId == null || readingId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryQualityAssessment reading id must not be blank.");
        }
        // HRA-051 required: pointId
        if (pointId == null || pointId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryQualityAssessment point id must not be blank.");
        }
        // HRA-051 required: assessmentStatus
        if (assessmentStatus == null) {
            throw new InvalidTelemetryValueException("TelemetryQualityAssessment assessment status must not be null.");
        }
        // HRA-051 required: inputQualityCodeId
        if (inputQualityCodeId == null || inputQualityCodeId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryQualityAssessment input quality code id must not be blank.");
        }
        // HRA-051 required: resolvedQualityCodeId
        if (resolvedQualityCodeId == null || resolvedQualityCodeId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryQualityAssessment resolved quality code id must not be blank.");
        }
        // HRA-051 required: trustLevel
        if (trustLevel == null) {
            throw new InvalidTelemetryValueException("TelemetryQualityAssessment trust level must not be null.");
        }
        // HRA-051 required: assessedAt
        if (assessedAt == null) {
            throw new InvalidTelemetryValueException("TelemetryQualityAssessment assessed at must not be null.");
        }

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
