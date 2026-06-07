/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : JPA entity for telemetry points.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for telemetry points.
 *
 * <p>Architecture role:
 * Persistence-only representation of telemetry data. It must not be exposed to domain, application,
 * or REST layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_telemetry_point")
public class TelemetryPointJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name_ar", nullable = true)
    private String nameAr;

    @Column(name = "name_fr", nullable = false)
    private String nameFr;

    @Column(name = "name_en", nullable = true)
    private String nameEn;

    @Column(name = "point_type_id", nullable = false)
    private String pointTypeId;

    @Column(name = "signal_type_id", nullable = false)
    private String signalTypeId;

    @Column(name = "unit_id", nullable = true)
    private String unitId;

    @Column(name = "default_aggregation_method_id", nullable = true)
    private String defaultAggregationMethodId;

    @Column(name = "sampling_period_seconds", nullable = true)
    private Integer samplingPeriodSeconds;

    @Column(name = "external_reference", nullable = true)
    private String externalReference;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected TelemetryPointJpaEntity() {
        // Required by JPA.
    }

    public TelemetryPointJpaEntity(
            String id,            String deviceId,            String code,            String nameAr,            String nameFr,            String nameEn,            String pointTypeId,            String signalTypeId,            String unitId,            String defaultAggregationMethodId,            Integer samplingPeriodSeconds,            String externalReference,            String status,            Instant createdAt,            Instant updatedAt) {
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
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getPointTypeId() {
        return pointTypeId;
    }

    public void setPointTypeId(String pointTypeId) {
        this.pointTypeId = pointTypeId;
    }

    public String getSignalTypeId() {
        return signalTypeId;
    }

    public void setSignalTypeId(String signalTypeId) {
        this.signalTypeId = signalTypeId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getDefaultAggregationMethodId() {
        return defaultAggregationMethodId;
    }

    public void setDefaultAggregationMethodId(String defaultAggregationMethodId) {
        this.defaultAggregationMethodId = defaultAggregationMethodId;
    }

    public Integer getSamplingPeriodSeconds() {
        return samplingPeriodSeconds;
    }

    public void setSamplingPeriodSeconds(Integer samplingPeriodSeconds) {
        this.samplingPeriodSeconds = samplingPeriodSeconds;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
