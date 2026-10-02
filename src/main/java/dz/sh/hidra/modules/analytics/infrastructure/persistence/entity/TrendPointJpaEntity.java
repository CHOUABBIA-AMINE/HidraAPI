/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrendPointJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TrendPoint.
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
     * Database-backed JPA entity for TrendPoint.
     */
    @Entity
    @Table(name = "hidra_analytics_trend_point")
    public class TrendPointJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "trend_analysis_id", nullable = false, length = 80)
    private String trendAnalysisId;

    @Column(name = "period_start", nullable = false)
    private Instant periodStart;

    @Column(name = "period_end", nullable = false)
    private Instant periodEnd;

    @Column(name = "value", nullable = false, precision = 18, scale = 6)
    private BigDecimal value;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "source_metric_value_id", nullable = true, length = 80)
    private String sourceMetricValueId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected TrendPointJpaEntity() {
            // Required by JPA.
        }

        public TrendPointJpaEntity(
                String id,
            String trendAnalysisId,
            Instant periodStart,
            Instant periodEnd,
            BigDecimal value,
            String unitId,
            String sourceMetricValueId,
            Instant createdAt
        ) {
            this.id = id;
        this.trendAnalysisId = trendAnalysisId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.value = value;
        this.unitId = unitId;
        this.sourceMetricValueId = sourceMetricValueId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String trendAnalysisId() {
        return trendAnalysisId;
    }


    public Instant periodStart() {
        return periodStart;
    }


    public Instant periodEnd() {
        return periodEnd;
    }


    public BigDecimal value() {
        return value;
    }


    public String unitId() {
        return unitId;
    }


    public String sourceMetricValueId() {
        return sourceMetricValueId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
