/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeterRunSnapshot
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
