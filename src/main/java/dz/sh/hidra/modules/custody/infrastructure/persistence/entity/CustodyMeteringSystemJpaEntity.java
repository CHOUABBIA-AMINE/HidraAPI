/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeteringSystemJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyMeteringSystem.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for CustodyMeteringSystem.
     */
    @Entity
    @Table(name = "hidra_custody_metering_system")
    public class CustodyMeteringSystemJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "metering_system_code", nullable = false, length = 80)
    private String meteringSystemCode;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "transfer_point_id", nullable = false, length = 80)
    private String transferPointId;

    @Column(name = "topology_asset_type_code", nullable = true, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "measurement_standard_id", nullable = true, length = 80)
    private String measurementStandardId;

    @Column(name = "calibration_certificate_id", nullable = true, length = 80)
    private String calibrationCertificateId;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected CustodyMeteringSystemJpaEntity() {
            // Required by JPA.
        }

        public CustodyMeteringSystemJpaEntity(
                String id,
            String meteringSystemCode,
            String name,
            String transferPointId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String measurementStandardId,
            String calibrationCertificateId,
            boolean active,
            Instant effectiveFrom,
            Instant effectiveTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.meteringSystemCode = meteringSystemCode;
        this.name = name;
        this.transferPointId = transferPointId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.measurementStandardId = measurementStandardId;
        this.calibrationCertificateId = calibrationCertificateId;
        this.active = active;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String meteringSystemCode() {
        return meteringSystemCode;
    }


    public String name() {
        return name;
    }


    public String transferPointId() {
        return transferPointId;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String measurementStandardId() {
        return measurementStandardId;
    }


    public String calibrationCertificateId() {
        return calibrationCertificateId;
    }


    public boolean active() {
        return active;
    }


    public Instant effectiveFrom() {
        return effectiveFrom;
    }


    public Instant effectiveTo() {
        return effectiveTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
