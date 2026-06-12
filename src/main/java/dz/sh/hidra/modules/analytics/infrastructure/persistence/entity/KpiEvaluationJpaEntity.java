/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : KpiEvaluationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for KpiEvaluation.
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
     * Database-backed JPA entity for KpiEvaluation.
     */
    @Entity
    @Table(name = "hidra_analytics_kpi_evaluation")
    public class KpiEvaluationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "kpi_definition_id", nullable = false, length = 80)
    private String kpiDefinitionId;

    @Column(name = "metric_value_id", nullable = true, length = 80)
    private String metricValueId;

    @Column(name = "scope_type", nullable = false, length = 80)
    private String scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "period_start", nullable = false)
    private Instant periodStart;

    @Column(name = "period_end", nullable = false)
    private Instant periodEnd;

    @Column(name = "value", nullable = false, precision = 18, scale = 6)
    private BigDecimal value;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "band_id", nullable = true, length = 80)
    private String bandId;

    @Enumerated(EnumType.STRING)
    @Column(name = "trend_direction", nullable = true, length = 40)
    private AnalyticsTrendDirection trendDirection;

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt;

        protected KpiEvaluationJpaEntity() {
            // Required by JPA.
        }

        public KpiEvaluationJpaEntity(
                String id,
            String kpiDefinitionId,
            String metricValueId,
            String scopeType,
            String scopeId,
            Instant periodStart,
            Instant periodEnd,
            BigDecimal value,
            String unitId,
            String bandId,
            AnalyticsTrendDirection trendDirection,
            Instant evaluatedAt
        ) {
            this.id = id;
        this.kpiDefinitionId = kpiDefinitionId;
        this.metricValueId = metricValueId;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.value = value;
        this.unitId = unitId;
        this.bandId = bandId;
        this.trendDirection = trendDirection;
        this.evaluatedAt = evaluatedAt;
        }


    public String id() {
        return id;
    }


    public String kpiDefinitionId() {
        return kpiDefinitionId;
    }


    public String metricValueId() {
        return metricValueId;
    }


    public String scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
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


    public String bandId() {
        return bandId;
    }


    public AnalyticsTrendDirection trendDirection() {
        return trendDirection;
    }


    public Instant evaluatedAt() {
        return evaluatedAt;
    }

    }
