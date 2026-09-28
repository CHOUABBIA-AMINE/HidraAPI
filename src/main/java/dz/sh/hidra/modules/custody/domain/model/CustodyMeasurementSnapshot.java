/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeasurementSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Accepted measurement snapshot.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Accepted measurement snapshot.
     *
         * @param id id
     * @param measurementPeriodId measurementPeriodId
     * @param batchId batchId
     * @param meterRunSnapshotId meterRunSnapshotId
     * @param telemetryReadingReferenceId telemetryReadingReferenceId
     * @param telemetryPointReferenceId telemetryPointReferenceId
     * @param measurementTypeId measurementTypeId
     * @param observedValue observedValue
     * @param observedUnitId observedUnitId
     * @param standardValue standardValue
     * @param standardUnitId standardUnitId
     * @param measuredAt measuredAt
     * @param acceptedForCustody acceptedForCustody
     * @param qualityFlagSnapshot qualityFlagSnapshot
     * @param createdAt createdAt
     */
    public record CustodyMeasurementSnapshot(
            String id,
        String measurementPeriodId,
        String batchId,
        String meterRunSnapshotId,
        String telemetryReadingReferenceId,
        String telemetryPointReferenceId,
        String measurementTypeId,
        BigDecimal observedValue,
        String observedUnitId,
        BigDecimal standardValue,
        String standardUnitId,
        Instant measuredAt,
        boolean acceptedForCustody,
        String qualityFlagSnapshot,
        Instant createdAt
    ) {

        public CustodyMeasurementSnapshot {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeasurementSnapshot id must not be blank.");
        }
        // HRA-051 required: measurementPeriodId
        if (measurementPeriodId == null || measurementPeriodId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeasurementSnapshot measurement period id must not be blank.");
        }
        // HRA-051 required: measurementTypeId
        if (measurementTypeId == null || measurementTypeId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeasurementSnapshot measurement type id must not be blank.");
        }
        // HRA-051 required: observedValue
        if (observedValue == null) {
            throw new InvalidCustodyValueException("CustodyMeasurementSnapshot observed value must not be null.");
        }
        // HRA-051 required: observedUnitId
        if (observedUnitId == null || observedUnitId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeasurementSnapshot observed unit id must not be blank.");
        }
        // HRA-051 required: measuredAt
        if (measuredAt == null) {
            throw new InvalidCustodyValueException("CustodyMeasurementSnapshot measured at must not be null.");
        }

        id = normalize(id);
        measurementPeriodId = normalize(measurementPeriodId);
        batchId = normalize(batchId);
        meterRunSnapshotId = normalize(meterRunSnapshotId);
        telemetryReadingReferenceId = normalize(telemetryReadingReferenceId);
        telemetryPointReferenceId = normalize(telemetryPointReferenceId);
        measurementTypeId = normalize(measurementTypeId);
        observedUnitId = normalize(observedUnitId);
        standardUnitId = normalize(standardUnitId);
        qualityFlagSnapshot = normalize(qualityFlagSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
