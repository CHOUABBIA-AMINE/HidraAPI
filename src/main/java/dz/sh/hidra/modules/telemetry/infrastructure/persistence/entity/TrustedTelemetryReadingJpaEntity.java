/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrustedTelemetryReadingJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TrustedTelemetryReading.
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
     * Database-backed JPA entity for TrustedTelemetryReading.
     */
    @Entity
    @Table(name = "hidra_telemetry_trusted_reading")
    public class TrustedTelemetryReadingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "reading_id", nullable = false, length = 80)
    private String readingId;

    @Column(name = "point_id", nullable = false, length = 80)
    private String pointId;

    @Column(name = "numeric_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal numericValue;

    @Column(name = "text_value", nullable = true, columnDefinition = "text")
    private String textValue;

    @Column(name = "boolean_value", nullable = true)
    private Boolean booleanValue;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "quality_code_id", nullable = false, length = 80)
    private String qualityCodeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "trust_level", nullable = false, length = 40)
    private TrustLevel trustLevel;

    @Column(name = "source_timestamp", nullable = false)
    private Instant sourceTimestamp;

    @Column(name = "trusted_at", nullable = false)
    private Instant trustedAt;

    @Column(name = "quality_assessment_id", nullable = false, length = 80)
    private String qualityAssessmentId;

    @Column(name = "topology_asset_type_code", nullable = true, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = true, length = 80)
    private String topologyAssetCode;

    @Column(name = "topology_snapshot_id", nullable = true, length = 80)
    private String topologySnapshotId;

    @Column(name = "ingestion_batch_id", nullable = true, length = 80)
    private String ingestionBatchId;

        protected TrustedTelemetryReadingJpaEntity() {
            // Required by JPA.
        }

        public TrustedTelemetryReadingJpaEntity(
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
            this.id = id;
        this.readingId = readingId;
        this.pointId = pointId;
        this.numericValue = numericValue;
        this.textValue = textValue;
        this.booleanValue = booleanValue;
        this.unitId = unitId;
        this.qualityCodeId = qualityCodeId;
        this.trustLevel = trustLevel;
        this.sourceTimestamp = sourceTimestamp;
        this.trustedAt = trustedAt;
        this.qualityAssessmentId = qualityAssessmentId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.topologySnapshotId = topologySnapshotId;
        this.ingestionBatchId = ingestionBatchId;
        }


    public String id() {
        return id;
    }


    public String readingId() {
        return readingId;
    }


    public String pointId() {
        return pointId;
    }


    public BigDecimal numericValue() {
        return numericValue;
    }


    public String textValue() {
        return textValue;
    }


    public Boolean booleanValue() {
        return booleanValue;
    }


    public String unitId() {
        return unitId;
    }


    public String qualityCodeId() {
        return qualityCodeId;
    }


    public TrustLevel trustLevel() {
        return trustLevel;
    }


    public Instant sourceTimestamp() {
        return sourceTimestamp;
    }


    public Instant trustedAt() {
        return trustedAt;
    }


    public String qualityAssessmentId() {
        return qualityAssessmentId;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCode() {
        return topologyAssetCode;
    }


    public String topologySnapshotId() {
        return topologySnapshotId;
    }


    public String ingestionBatchId() {
        return ingestionBatchId;
    }

    }
