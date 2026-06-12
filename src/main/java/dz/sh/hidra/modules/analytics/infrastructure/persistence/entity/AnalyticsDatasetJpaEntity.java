/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsDataset.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import dz.sh.hidra.modules.analytics.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AnalyticsDataset.
     */
    @Entity
    @Table(name = "hidra_analytics_dataset")
    public class AnalyticsDatasetJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "subject_area_id", nullable = false, length = 80)
    private String subjectAreaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "dataset_type", nullable = false, length = 40)
    private AnalyticsDatasetType datasetType;

    @Enumerated(EnumType.STRING)
    @Column(name = "refresh_mode", nullable = false, length = 40)
    private AnalyticsRefreshMode refreshMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "lineage_status", nullable = false, length = 40)
    private AnalyticsLineageStatus lineageStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality_status", nullable = false, length = 40)
    private AnalyticsQualityStatus qualityStatus;

    @Column(name = "schema_version", nullable = true, length = 80)
    private String schemaVersion;

    @Column(name = "created_from", nullable = true, length = 255)
    private String createdFrom;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AnalyticsDatasetJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsDatasetJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String subjectAreaId,
            AnalyticsDatasetType datasetType,
            AnalyticsRefreshMode refreshMode,
            AnalyticsLineageStatus lineageStatus,
            AnalyticsQualityStatus qualityStatus,
            String schemaVersion,
            String createdFrom,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.subjectAreaId = subjectAreaId;
        this.datasetType = datasetType;
        this.refreshMode = refreshMode;
        this.lineageStatus = lineageStatus;
        this.qualityStatus = qualityStatus;
        this.schemaVersion = schemaVersion;
        this.createdFrom = createdFrom;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
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


    public String subjectAreaId() {
        return subjectAreaId;
    }


    public AnalyticsDatasetType datasetType() {
        return datasetType;
    }


    public AnalyticsRefreshMode refreshMode() {
        return refreshMode;
    }


    public AnalyticsLineageStatus lineageStatus() {
        return lineageStatus;
    }


    public AnalyticsQualityStatus qualityStatus() {
        return qualityStatus;
    }


    public String schemaVersion() {
        return schemaVersion;
    }


    public String createdFrom() {
        return createdFrom;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
