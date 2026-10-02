/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrustedTelemetryReading
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Downstream trusted reading contract.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Downstream trusted reading contract.
     *
         * @param id id
     * @param readingId readingId
     * @param pointId pointId
     * @param numericValue numericValue
     * @param textValue textValue
     * @param booleanValue booleanValue
     * @param unitId unitId
     * @param qualityCodeId qualityCodeId
     * @param trustLevel trustLevel
     * @param sourceTimestamp sourceTimestamp
     * @param trustedAt trustedAt
     * @param qualityAssessmentId qualityAssessmentId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param topologySnapshotId topologySnapshotId
     * @param ingestionBatchId ingestionBatchId
     */
    public record TrustedTelemetryReading(
            String id,
        String readingId,
        String pointId,
        BigDecimal numericValue,
        String textValue,
        Boolean booleanValue,
        String unitId,
        String qualityCodeId,
        TrustLevel trustLevel,
        Instant sourceTimestamp,
        Instant trustedAt,
        String qualityAssessmentId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        String topologySnapshotId,
        String ingestionBatchId
    ) {

        public TrustedTelemetryReading {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TrustedTelemetryReading id must not be blank.");
        }
        // HRA-051 required: readingId
        if (readingId == null || readingId.isBlank()) {
            throw new InvalidTelemetryValueException("TrustedTelemetryReading reading id must not be blank.");
        }
        // HRA-051 required: pointId
        if (pointId == null || pointId.isBlank()) {
            throw new InvalidTelemetryValueException("TrustedTelemetryReading point id must not be blank.");
        }
        // HRA-051 required: qualityCodeId
        if (qualityCodeId == null || qualityCodeId.isBlank()) {
            throw new InvalidTelemetryValueException("TrustedTelemetryReading quality code id must not be blank.");
        }
        // HRA-051 required: trustLevel
        if (trustLevel == null) {
            throw new InvalidTelemetryValueException("TrustedTelemetryReading trust level must not be null.");
        }
        // HRA-051 required: sourceTimestamp
        if (sourceTimestamp == null) {
            throw new InvalidTelemetryValueException("TrustedTelemetryReading source timestamp must not be null.");
        }
        // HRA-051 required: trustedAt
        if (trustedAt == null) {
            throw new InvalidTelemetryValueException("TrustedTelemetryReading trusted at must not be null.");
        }
        // HRA-051 required: qualityAssessmentId
        if (qualityAssessmentId == null || qualityAssessmentId.isBlank()) {
            throw new InvalidTelemetryValueException("TrustedTelemetryReading quality assessment id must not be blank.");
        }

        id = normalize(id);
        readingId = normalize(readingId);
        pointId = normalize(pointId);
        textValue = normalize(textValue);
        unitId = normalize(unitId);
        qualityCodeId = normalize(qualityCodeId);
        qualityAssessmentId = normalize(qualityAssessmentId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        topologySnapshotId = normalize(topologySnapshotId);
        ingestionBatchId = normalize(ingestionBatchId);
        }
        public boolean hasExactlyOneValue() {
            int count = 0;
            if (numericValue != null) {
                count++;
            }
            if (textValue != null && !textValue.isBlank()) {
                count++;
            }
            if (booleanValue != null) {
                count++;
            }
            return count == 1;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
