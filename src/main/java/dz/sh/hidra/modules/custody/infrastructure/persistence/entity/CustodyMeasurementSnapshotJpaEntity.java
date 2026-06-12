/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeasurementSnapshotJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyMeasurementSnapshot.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for CustodyMeasurementSnapshot.
     */
    @Entity
    @Table(name = "hidra_custody_measurement_snapshot")
    public class CustodyMeasurementSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "measurement_period_id", nullable = false, length = 80)
    private String measurementPeriodId;

    @Column(name = "batch_id", nullable = true, length = 80)
    private String batchId;

    @Column(name = "meter_run_snapshot_id", nullable = true, length = 80)
    private String meterRunSnapshotId;

    @Column(name = "telemetry_reading_reference_id", nullable = true, length = 80)
    private String telemetryReadingReferenceId;

    @Column(name = "telemetry_point_reference_id", nullable = true, length = 80)
    private String telemetryPointReferenceId;

    @Column(name = "measurement_type_id", nullable = false, length = 80)
    private String measurementTypeId;

    @Column(name = "observed_value", nullable = false, precision = 18, scale = 6)
    private BigDecimal observedValue;

    @Column(name = "observed_unit_id", nullable = false, length = 80)
    private String observedUnitId;

    @Column(name = "standard_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal standardValue;

    @Column(name = "standard_unit_id", nullable = true, length = 80)
    private String standardUnitId;

    @Column(name = "measured_at", nullable = false)
    private Instant measuredAt;

    @Column(name = "accepted_for_custody", nullable = false)
    private boolean acceptedForCustody;

    @Column(name = "quality_flag_snapshot", nullable = true, length = 80)
    private String qualityFlagSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected CustodyMeasurementSnapshotJpaEntity() {
            // Required by JPA.
        }

        public CustodyMeasurementSnapshotJpaEntity(
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
            this.id = id;
        this.measurementPeriodId = measurementPeriodId;
        this.batchId = batchId;
        this.meterRunSnapshotId = meterRunSnapshotId;
        this.telemetryReadingReferenceId = telemetryReadingReferenceId;
        this.telemetryPointReferenceId = telemetryPointReferenceId;
        this.measurementTypeId = measurementTypeId;
        this.observedValue = observedValue;
        this.observedUnitId = observedUnitId;
        this.standardValue = standardValue;
        this.standardUnitId = standardUnitId;
        this.measuredAt = measuredAt;
        this.acceptedForCustody = acceptedForCustody;
        this.qualityFlagSnapshot = qualityFlagSnapshot;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String measurementPeriodId() {
        return measurementPeriodId;
    }


    public String batchId() {
        return batchId;
    }


    public String meterRunSnapshotId() {
        return meterRunSnapshotId;
    }


    public String telemetryReadingReferenceId() {
        return telemetryReadingReferenceId;
    }


    public String telemetryPointReferenceId() {
        return telemetryPointReferenceId;
    }


    public String measurementTypeId() {
        return measurementTypeId;
    }


    public BigDecimal observedValue() {
        return observedValue;
    }


    public String observedUnitId() {
        return observedUnitId;
    }


    public BigDecimal standardValue() {
        return standardValue;
    }


    public String standardUnitId() {
        return standardUnitId;
    }


    public Instant measuredAt() {
        return measuredAt;
    }


    public boolean acceptedForCustody() {
        return acceptedForCustody;
    }


    public String qualityFlagSnapshot() {
        return qualityFlagSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
