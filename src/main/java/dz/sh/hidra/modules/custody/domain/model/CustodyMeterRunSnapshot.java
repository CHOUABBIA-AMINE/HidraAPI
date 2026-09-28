/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeterRunSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Meter-run snapshot.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import java.time.Instant;

    /**
     * Meter-run snapshot.
     *
         * @param id id
     * @param measurementPeriodId measurementPeriodId
     * @param meteringSystemId meteringSystemId
     * @param meterRunCodeSnapshot meterRunCodeSnapshot
     * @param meterSerialSnapshot meterSerialSnapshot
     * @param calibrationCertificateSnapshot calibrationCertificateSnapshot
     * @param configurationSnapshotJson configurationSnapshotJson
     * @param snapshotAt snapshotAt
     * @param snapshotByActorId snapshotByActorId
     * @param createdAt createdAt
     */
    public record CustodyMeterRunSnapshot(
            String id,
        String measurementPeriodId,
        String meteringSystemId,
        String meterRunCodeSnapshot,
        String meterSerialSnapshot,
        String calibrationCertificateSnapshot,
        String configurationSnapshotJson,
        Instant snapshotAt,
        String snapshotByActorId,
        Instant createdAt
    ) {

        public CustodyMeterRunSnapshot {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeterRunSnapshot id must not be blank.");
        }
        // HRA-051 required: measurementPeriodId
        if (measurementPeriodId == null || measurementPeriodId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeterRunSnapshot measurement period id must not be blank.");
        }
        // HRA-051 required: meteringSystemId
        if (meteringSystemId == null || meteringSystemId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyMeterRunSnapshot metering system id must not be blank.");
        }
        // HRA-051 required: snapshotAt
        if (snapshotAt == null) {
            throw new InvalidCustodyValueException("CustodyMeterRunSnapshot snapshot at must not be null.");
        }

        id = normalize(id);
        measurementPeriodId = normalize(measurementPeriodId);
        meteringSystemId = normalize(meteringSystemId);
        meterRunCodeSnapshot = normalize(meterRunCodeSnapshot);
        meterSerialSnapshot = normalize(meterSerialSnapshot);
        calibrationCertificateSnapshot = normalize(calibrationCertificateSnapshot);
        configurationSnapshotJson = normalize(configurationSnapshotJson);
        snapshotByActorId = normalize(snapshotByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
