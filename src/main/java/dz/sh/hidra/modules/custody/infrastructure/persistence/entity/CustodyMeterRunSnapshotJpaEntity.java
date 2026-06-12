/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeterRunSnapshotJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyMeterRunSnapshot.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for CustodyMeterRunSnapshot.
     */
    @Entity
    @Table(name = "hidra_custody_meter_run_snapshot")
    public class CustodyMeterRunSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "measurement_period_id", nullable = false, length = 80)
    private String measurementPeriodId;

    @Column(name = "metering_system_id", nullable = false, length = 80)
    private String meteringSystemId;

    @Column(name = "meter_run_code_snapshot", nullable = false, length = 160)
    private String meterRunCodeSnapshot;

    @Column(name = "meter_serial_snapshot", nullable = true, length = 160)
    private String meterSerialSnapshot;

    @Column(name = "calibration_certificate_snapshot", nullable = true, length = 160)
    private String calibrationCertificateSnapshot;

    @Column(name = "configuration_snapshot_json", nullable = true, columnDefinition = "jsonb")
    private String configurationSnapshotJson;

    @Column(name = "snapshot_at", nullable = false)
    private Instant snapshotAt;

    @Column(name = "snapshot_by_actor_id", nullable = true, length = 80)
    private String snapshotByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected CustodyMeterRunSnapshotJpaEntity() {
            // Required by JPA.
        }

        public CustodyMeterRunSnapshotJpaEntity(
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
            this.id = id;
        this.measurementPeriodId = measurementPeriodId;
        this.meteringSystemId = meteringSystemId;
        this.meterRunCodeSnapshot = meterRunCodeSnapshot;
        this.meterSerialSnapshot = meterSerialSnapshot;
        this.calibrationCertificateSnapshot = calibrationCertificateSnapshot;
        this.configurationSnapshotJson = configurationSnapshotJson;
        this.snapshotAt = snapshotAt;
        this.snapshotByActorId = snapshotByActorId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String measurementPeriodId() {
        return measurementPeriodId;
    }


    public String meteringSystemId() {
        return meteringSystemId;
    }


    public String meterRunCodeSnapshot() {
        return meterRunCodeSnapshot;
    }


    public String meterSerialSnapshot() {
        return meterSerialSnapshot;
    }


    public String calibrationCertificateSnapshot() {
        return calibrationCertificateSnapshot;
    }


    public String configurationSnapshotJson() {
        return configurationSnapshotJson;
    }


    public Instant snapshotAt() {
        return snapshotAt;
    }


    public String snapshotByActorId() {
        return snapshotByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
