/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDefectJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PipelineDefect.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PipelineDefect.
     */
    @Entity
    @Table(name = "hidra_integrity_pipeline_defect")
    public class PipelineDefectJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "defect_number", nullable = false, length = 80)
    private String defectNumber;

    @Column(name = "defect_type_id", nullable = false, length = 80)
    private String defectTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "threat_type", nullable = true, length = 80)
    private ThreatType threatType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private DefectStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = true, length = 40)
    private FindingSeverity severity;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code_snapshot", nullable = true, length = 160)
    private String topologyAssetCodeSnapshot;

    @Column(name = "kilometer_point", nullable = true, precision = 14, scale = 4)
    private BigDecimal kilometerPoint;

    @Column(name = "latitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "detected_at", nullable = false)
    private Instant detectedAt;

    @Column(name = "closed_at", nullable = true)
    private Instant closedAt;

    @Column(name = "source_finding_id", nullable = true, length = 80)
    private String sourceFindingId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PipelineDefectJpaEntity() {
            // Required by JPA.
        }

        public PipelineDefectJpaEntity(
                String id,
            String defectNumber,
            String defectTypeId,
            ThreatType threatType,
            DefectStatus status,
            FindingSeverity severity,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCodeSnapshot,
            BigDecimal kilometerPoint,
            BigDecimal latitude,
            BigDecimal longitude,
            String description,
            Instant detectedAt,
            Instant closedAt,
            String sourceFindingId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.defectNumber = defectNumber;
        this.defectTypeId = defectTypeId;
        this.threatType = threatType;
        this.status = status;
        this.severity = severity;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCodeSnapshot = topologyAssetCodeSnapshot;
        this.kilometerPoint = kilometerPoint;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.detectedAt = detectedAt;
        this.closedAt = closedAt;
        this.sourceFindingId = sourceFindingId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String defectNumber() {
        return defectNumber;
    }


    public String defectTypeId() {
        return defectTypeId;
    }


    public ThreatType threatType() {
        return threatType;
    }


    public DefectStatus status() {
        return status;
    }


    public FindingSeverity severity() {
        return severity;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCodeSnapshot() {
        return topologyAssetCodeSnapshot;
    }


    public BigDecimal kilometerPoint() {
        return kilometerPoint;
    }


    public BigDecimal latitude() {
        return latitude;
    }


    public BigDecimal longitude() {
        return longitude;
    }


    public String description() {
        return description;
    }


    public Instant detectedAt() {
        return detectedAt;
    }


    public Instant closedAt() {
        return closedAt;
    }


    public String sourceFindingId() {
        return sourceFindingId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
