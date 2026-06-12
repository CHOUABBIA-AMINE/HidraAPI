/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsInsightJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsInsight.
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
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AnalyticsInsight.
     */
    @Entity
    @Table(name = "hidra_analytics_insight")
    public class AnalyticsInsightJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "insight_type", nullable = false, length = 120)
    private String insightType;

    @Column(name = "subject_area_id", nullable = false, length = 80)
    private String subjectAreaId;

    @Column(name = "scope_type", nullable = false, length = 80)
    private String scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "summary", nullable = false, length = 3000)
    private String summary;

    @Column(name = "severity_id", nullable = true, length = 80)
    private String severityId;

    @Column(name = "confidence_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal confidenceScore;

    @Column(name = "source_projection_snapshot_id", nullable = true, length = 80)
    private String sourceProjectionSnapshotId;

    @Column(name = "source_trend_analysis_id", nullable = true, length = 80)
    private String sourceTrendAnalysisId;

    @Column(name = "source_model_run_id", nullable = true, length = 80)
    private String sourceModelRunId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private AnalyticsInsightStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AnalyticsInsightJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsInsightJpaEntity(
                String id,
            String insightType,
            String subjectAreaId,
            String scopeType,
            String scopeId,
            String title,
            String summary,
            String severityId,
            BigDecimal confidenceScore,
            String sourceProjectionSnapshotId,
            String sourceTrendAnalysisId,
            String sourceModelRunId,
            AnalyticsInsightStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.insightType = insightType;
        this.subjectAreaId = subjectAreaId;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.title = title;
        this.summary = summary;
        this.severityId = severityId;
        this.confidenceScore = confidenceScore;
        this.sourceProjectionSnapshotId = sourceProjectionSnapshotId;
        this.sourceTrendAnalysisId = sourceTrendAnalysisId;
        this.sourceModelRunId = sourceModelRunId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String insightType() {
        return insightType;
    }


    public String subjectAreaId() {
        return subjectAreaId;
    }


    public String scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public String title() {
        return title;
    }


    public String summary() {
        return summary;
    }


    public String severityId() {
        return severityId;
    }


    public BigDecimal confidenceScore() {
        return confidenceScore;
    }


    public String sourceProjectionSnapshotId() {
        return sourceProjectionSnapshotId;
    }


    public String sourceTrendAnalysisId() {
        return sourceTrendAnalysisId;
    }


    public String sourceModelRunId() {
        return sourceModelRunId;
    }


    public AnalyticsInsightStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
