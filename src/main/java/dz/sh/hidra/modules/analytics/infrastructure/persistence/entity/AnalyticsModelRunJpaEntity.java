/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsModelRunJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsModelRun.
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
     * Database-backed JPA entity for AnalyticsModelRun.
     */
    @Entity
    @Table(name = "hidra_analytics_model_run")
    public class AnalyticsModelRunJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "analytics_model_version_id", nullable = false, length = 80)
    private String analyticsModelVersionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "run_type", nullable = false, length = 40)
    private AnalyticsModelRunType runType;

    @Enumerated(EnumType.STRING)
    @Column(name = "run_status", nullable = false, length = 40)
    private AnalyticsRunStatus runStatus;

    @Column(name = "input_dataset_version_id", nullable = true, length = 80)
    private String inputDatasetVersionId;

    @Column(name = "scope_type", nullable = true, length = 80)
    private String scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "period_start", nullable = true)
    private Instant periodStart;

    @Column(name = "period_end", nullable = true)
    private Instant periodEnd;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "output_dataset_version_id", nullable = true, length = 80)
    private String outputDatasetVersionId;

    @Column(name = "error_code", nullable = true, length = 120)
    private String errorCode;

    @Column(name = "error_message", nullable = true, length = 2000)
    private String errorMessage;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AnalyticsModelRunJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsModelRunJpaEntity(
                String id,
            String analyticsModelVersionId,
            AnalyticsModelRunType runType,
            AnalyticsRunStatus runStatus,
            String inputDatasetVersionId,
            String scopeType,
            String scopeId,
            Instant periodStart,
            Instant periodEnd,
            Instant startedAt,
            Instant completedAt,
            String outputDatasetVersionId,
            String errorCode,
            String errorMessage,
            String correlationId,
            Instant createdAt
        ) {
            this.id = id;
        this.analyticsModelVersionId = analyticsModelVersionId;
        this.runType = runType;
        this.runStatus = runStatus;
        this.inputDatasetVersionId = inputDatasetVersionId;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.outputDatasetVersionId = outputDatasetVersionId;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String analyticsModelVersionId() {
        return analyticsModelVersionId;
    }


    public AnalyticsModelRunType runType() {
        return runType;
    }


    public AnalyticsRunStatus runStatus() {
        return runStatus;
    }


    public String inputDatasetVersionId() {
        return inputDatasetVersionId;
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


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String outputDatasetVersionId() {
        return outputDatasetVersionId;
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
