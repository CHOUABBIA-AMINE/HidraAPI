/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricEvaluationRunJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MetricEvaluationRun.
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
     * Database-backed JPA entity for MetricEvaluationRun.
     */
    @Entity
    @Table(name = "hidra_analytics_metric_evaluation_run")
    public class MetricEvaluationRunJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "metric_definition_version_id", nullable = false, length = 80)
    private String metricDefinitionVersionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "run_status", nullable = false, length = 40)
    private AnalyticsRunStatus runStatus;

    @Column(name = "period_start", nullable = false)
    private Instant periodStart;

    @Column(name = "period_end", nullable = false)
    private Instant periodEnd;

    @Column(name = "scope_type", nullable = false, length = 80)
    private String scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "records_read", nullable = true)
    private Long recordsRead;

    @Column(name = "records_produced", nullable = true)
    private Long recordsProduced;

    @Column(name = "error_code", nullable = true, length = 120)
    private String errorCode;

    @Column(name = "error_message", nullable = true, length = 2000)
    private String errorMessage;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected MetricEvaluationRunJpaEntity() {
            // Required by JPA.
        }

        public MetricEvaluationRunJpaEntity(
                String id,
            String metricDefinitionVersionId,
            AnalyticsRunStatus runStatus,
            Instant periodStart,
            Instant periodEnd,
            String scopeType,
            String scopeId,
            Instant startedAt,
            Instant completedAt,
            Long recordsRead,
            Long recordsProduced,
            String errorCode,
            String errorMessage,
            String correlationId,
            Instant createdAt
        ) {
            this.id = id;
        this.metricDefinitionVersionId = metricDefinitionVersionId;
        this.runStatus = runStatus;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.recordsRead = recordsRead;
        this.recordsProduced = recordsProduced;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String metricDefinitionVersionId() {
        return metricDefinitionVersionId;
    }


    public AnalyticsRunStatus runStatus() {
        return runStatus;
    }


    public Instant periodStart() {
        return periodStart;
    }


    public Instant periodEnd() {
        return periodEnd;
    }


    public String scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public Long recordsRead() {
        return recordsRead;
    }


    public Long recordsProduced() {
        return recordsProduced;
    }


    public String errorCode() {
        return errorCode;
    }


    public String errorMessage() {
        return errorMessage;
    }


    public String correlationId() {
        return correlationId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
