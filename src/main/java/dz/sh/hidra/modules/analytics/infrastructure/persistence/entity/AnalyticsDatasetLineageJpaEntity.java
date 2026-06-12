/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetLineageJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsDatasetLineage.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AnalyticsDatasetLineage.
     */
    @Entity
    @Table(name = "hidra_analytics_dataset_lineage")
    public class AnalyticsDatasetLineageJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "dataset_version_id", nullable = false, length = 80)
    private String datasetVersionId;

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Column(name = "source_object_type", nullable = false, length = 120)
    private String sourceObjectType;

    @Column(name = "source_object_id", nullable = false, length = 120)
    private String sourceObjectId;

    @Column(name = "source_snapshot_id", nullable = true, length = 120)
    private String sourceSnapshotId;

    @Column(name = "source_version", nullable = true, length = 80)
    private String sourceVersion;

    @Column(name = "source_period_start", nullable = true)
    private Instant sourcePeriodStart;

    @Column(name = "source_period_end", nullable = true)
    private Instant sourcePeriodEnd;

    @Column(name = "lineage_role", nullable = false, length = 120)
    private String lineageRole;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AnalyticsDatasetLineageJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsDatasetLineageJpaEntity(
                String id,
            String datasetVersionId,
            String sourceModule,
            String sourceObjectType,
            String sourceObjectId,
            String sourceSnapshotId,
            String sourceVersion,
            Instant sourcePeriodStart,
            Instant sourcePeriodEnd,
            String lineageRole,
            Instant createdAt
        ) {
            this.id = id;
        this.datasetVersionId = datasetVersionId;
        this.sourceModule = sourceModule;
        this.sourceObjectType = sourceObjectType;
        this.sourceObjectId = sourceObjectId;
        this.sourceSnapshotId = sourceSnapshotId;
        this.sourceVersion = sourceVersion;
        this.sourcePeriodStart = sourcePeriodStart;
        this.sourcePeriodEnd = sourcePeriodEnd;
        this.lineageRole = lineageRole;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String datasetVersionId() {
        return datasetVersionId;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceObjectType() {
        return sourceObjectType;
    }


    public String sourceObjectId() {
        return sourceObjectId;
    }


    public String sourceSnapshotId() {
        return sourceSnapshotId;
    }


    public String sourceVersion() {
        return sourceVersion;
    }


    public Instant sourcePeriodStart() {
        return sourcePeriodStart;
    }


    public Instant sourcePeriodEnd() {
        return sourcePeriodEnd;
    }


    public String lineageRole() {
        return lineageRole;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
