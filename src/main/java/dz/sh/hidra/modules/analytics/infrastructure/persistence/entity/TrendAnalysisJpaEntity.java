/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrendAnalysisJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TrendAnalysis.
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
     * Database-backed JPA entity for TrendAnalysis.
     */
    @Entity
    @Table(name = "hidra_analytics_trend_analysis")
    public class TrendAnalysisJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "subject_area_id", nullable = false, length = 80)
    private String subjectAreaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "trend_type", nullable = false, length = 40)
    private AnalyticsTrendDirection trendType;

    @Column(name = "scope_type", nullable = false, length = 80)
    private String scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "metric_definition_id", nullable = true, length = 80)
    private String metricDefinitionId;

    @Column(name = "period_start", nullable = false)
    private Instant periodStart;

    @Column(name = "period_end", nullable = false)
    private Instant periodEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "trend_direction", nullable = false, length = 40)
    private AnalyticsTrendDirection trendDirection;

    @Column(name = "confidence_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal confidenceScore;

    @Column(name = "strength_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal strengthScore;

    @Column(name = "detected_at", nullable = false)
    private Instant detectedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected TrendAnalysisJpaEntity() {
            // Required by JPA.
        }

        public TrendAnalysisJpaEntity(
                String id,
            String subjectAreaId,
            AnalyticsTrendDirection trendType,
            String scopeType,
            String scopeId,
            String metricDefinitionId,
            Instant periodStart,
            Instant periodEnd,
            AnalyticsTrendDirection trendDirection,
            BigDecimal confidenceScore,
            BigDecimal strengthScore,
            Instant detectedAt,
            Instant createdAt
        ) {
            this.id = id;
        this.subjectAreaId = subjectAreaId;
        this.trendType = trendType;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.metricDefinitionId = metricDefinitionId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.trendDirection = trendDirection;
        this.confidenceScore = confidenceScore;
        this.strengthScore = strengthScore;
        this.detectedAt = detectedAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String subjectAreaId() {
        return subjectAreaId;
    }


    public AnalyticsTrendDirection trendType() {
        return trendType;
    }


    public String scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public String metricDefinitionId() {
        return metricDefinitionId;
    }


    public Instant periodStart() {
        return periodStart;
    }


    public Instant periodEnd() {
        return periodEnd;
    }


    public AnalyticsTrendDirection trendDirection() {
        return trendDirection;
    }


    public BigDecimal confidenceScore() {
        return confidenceScore;
    }


    public BigDecimal strengthScore() {
        return strengthScore;
    }


    public Instant detectedAt() {
        return detectedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
