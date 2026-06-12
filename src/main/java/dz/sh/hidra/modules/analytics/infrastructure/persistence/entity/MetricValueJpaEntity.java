/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricValueJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MetricValue.
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
     * Database-backed JPA entity for MetricValue.
     */
    @Entity
    @Table(name = "hidra_analytics_metric_value")
    public class MetricValueJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "metric_evaluation_run_id", nullable = false, length = 80)
    private String metricEvaluationRunId;

    @Column(name = "metric_definition_id", nullable = false, length = 80)
    private String metricDefinitionId;

    @Column(name = "metric_definition_version_id", nullable = false, length = 80)
    private String metricDefinitionVersionId;

    @Column(name = "scope_type", nullable = false, length = 80)
    private String scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "period_start", nullable = false)
    private Instant periodStart;

    @Column(name = "period_end", nullable = false)
    private Instant periodEnd;

    @Column(name = "value_numeric", nullable = true, precision = 18, scale = 6)
    private BigDecimal valueNumeric;

    @Column(name = "value_text", nullable = true, length = 2000)
    private String valueText;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality_status", nullable = false, length = 40)
    private AnalyticsQualityStatus qualityStatus;

    @Column(name = "calculated_at", nullable = false)
    private Instant calculatedAt;

        protected MetricValueJpaEntity() {
            // Required by JPA.
        }

        public MetricValueJpaEntity(
                String id,
            String metricEvaluationRunId,
            String metricDefinitionId,
            String metricDefinitionVersionId,
            String scopeType,
            String scopeId,
            Instant periodStart,
            Instant periodEnd,
            BigDecimal valueNumeric,
            String valueText,
            String unitId,
            AnalyticsQualityStatus qualityStatus,
            Instant calculatedAt
        ) {
            this.id = id;
        this.metricEvaluationRunId = metricEvaluationRunId;
        this.metricDefinitionId = metricDefinitionId;
        this.metricDefinitionVersionId = metricDefinitionVersionId;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.valueNumeric = valueNumeric;
        this.valueText = valueText;
        this.unitId = unitId;
        this.qualityStatus = qualityStatus;
        this.calculatedAt = calculatedAt;
        }


    public String id() {
        return id;
    }


    public String metricEvaluationRunId() {
        return metricEvaluationRunId;
    }


    public String metricDefinitionId() {
        return metricDefinitionId;
    }


    public String metricDefinitionVersionId() {
        return metricDefinitionVersionId;
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


    public BigDecimal valueNumeric() {
        return valueNumeric;
    }


    public String valueText() {
        return valueText;
    }


    public String unitId() {
        return unitId;
    }


    public AnalyticsQualityStatus qualityStatus() {
        return qualityStatus;
    }


    public Instant calculatedAt() {
        return calculatedAt;
    }

    }
