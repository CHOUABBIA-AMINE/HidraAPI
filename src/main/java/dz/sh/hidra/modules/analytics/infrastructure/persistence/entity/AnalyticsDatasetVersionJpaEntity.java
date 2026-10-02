/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetVersionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsDatasetVersion.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AnalyticsDatasetVersion.
     */
    @Entity
    @Table(name = "hidra_analytics_dataset_version")
    public class AnalyticsDatasetVersionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "dataset_id", nullable = false, length = 80)
    private String datasetId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "schema_hash", nullable = false, length = 160)
    private String schemaHash;

    @Column(name = "data_hash", nullable = false, length = 160)
    private String dataHash;

    @Column(name = "row_count", nullable = true)
    private Long rowCount;

    @Column(name = "period_start", nullable = true)
    private Instant periodStart;

    @Column(name = "period_end", nullable = true)
    private Instant periodEnd;

    @Column(name = "quality_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal qualityScore;

    @Column(name = "published", nullable = false)
    private boolean published;

    @Column(name = "published_at", nullable = true)
    private Instant publishedAt;

    @Column(name = "published_by_actor_id", nullable = true, length = 80)
    private String publishedByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AnalyticsDatasetVersionJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsDatasetVersionJpaEntity(
                String id,
            String datasetId,
            int versionNumber,
            String schemaHash,
            String dataHash,
            Long rowCount,
            Instant periodStart,
            Instant periodEnd,
            BigDecimal qualityScore,
            boolean published,
            Instant publishedAt,
            String publishedByActorId,
            Instant createdAt
        ) {
            this.id = id;
        this.datasetId = datasetId;
        this.versionNumber = versionNumber;
        this.schemaHash = schemaHash;
        this.dataHash = dataHash;
        this.rowCount = rowCount;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.qualityScore = qualityScore;
        this.published = published;
        this.publishedAt = publishedAt;
        this.publishedByActorId = publishedByActorId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String datasetId() {
        return datasetId;
    }


    public int versionNumber() {
        return versionNumber;
    }


    public String schemaHash() {
        return schemaHash;
    }


    public String dataHash() {
        return dataHash;
    }


    public Long rowCount() {
        return rowCount;
    }


    public Instant periodStart() {
        return periodStart;
    }


    public Instant periodEnd() {
        return periodEnd;
    }


    public BigDecimal qualityScore() {
        return qualityScore;
    }


    public boolean published() {
        return published;
    }


    public Instant publishedAt() {
        return publishedAt;
    }


    public String publishedByActorId() {
        return publishedByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
