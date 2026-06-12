/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetryPoint.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for TelemetryPoint.
     */
    @Entity
    @Table(name = "hidra_telemetry_point")
    public class TelemetryPointJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "device_id", nullable = false, length = 80)
    private String deviceId;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "point_type_id", nullable = false, length = 80)
    private String pointTypeId;

    @Column(name = "signal_type_id", nullable = false, length = 80)
    private String signalTypeId;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "default_aggregation_method_id", nullable = true, length = 80)
    private String defaultAggregationMethodId;

    @Column(name = "sampling_period_seconds", nullable = true)
    private Integer samplingPeriodSeconds;

    @Column(name = "external_reference", nullable = true, length = 500)
    private String externalReference;

    @Column(name = "deadband_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal deadbandValue;

    @Column(name = "min_operational_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal minOperationalValue;

    @Column(name = "max_operational_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal maxOperationalValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TelemetryLifecycleStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected TelemetryPointJpaEntity() {
            // Required by JPA.
        }

        public TelemetryPointJpaEntity(
                String id,
            String deviceId,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String pointTypeId,
            String signalTypeId,
            String unitId,
            String defaultAggregationMethodId,
            Integer samplingPeriodSeconds,
            String externalReference,
            BigDecimal deadbandValue,
            BigDecimal minOperationalValue,
            BigDecimal maxOperationalValue,
            TelemetryLifecycleStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.deviceId = deviceId;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.pointTypeId = pointTypeId;
        this.signalTypeId = signalTypeId;
        this.unitId = unitId;
        this.defaultAggregationMethodId = defaultAggregationMethodId;
        this.samplingPeriodSeconds = samplingPeriodSeconds;
        this.externalReference = externalReference;
        this.deadbandValue = deadbandValue;
        this.minOperationalValue = minOperationalValue;
        this.maxOperationalValue = maxOperationalValue;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String deviceId() {
        return deviceId;
    }


    public String code() {
        return code;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String pointTypeId() {
        return pointTypeId;
    }


    public String signalTypeId() {
        return signalTypeId;
    }


    public String unitId() {
        return unitId;
    }


    public String defaultAggregationMethodId() {
        return defaultAggregationMethodId;
    }


    public Integer samplingPeriodSeconds() {
        return samplingPeriodSeconds;
    }


    public String externalReference() {
        return externalReference;
    }


    public BigDecimal deadbandValue() {
        return deadbandValue;
    }


    public BigDecimal minOperationalValue() {
        return minOperationalValue;
    }


    public BigDecimal maxOperationalValue() {
        return maxOperationalValue;
    }


    public TelemetryLifecycleStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
