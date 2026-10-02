/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRunJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportRun.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.entity;

import dz.sh.hidra.modules.reporting.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ReportRun.
     */
    @Entity
    @Table(name = "hidra_reporting_run")
    public class ReportRunJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_request_id", nullable = false, length = 80)
    private String reportRequestId;

    @Column(name = "report_definition_id", nullable = false, length = 80)
    private String reportDefinitionId;

    @Column(name = "template_version_id", nullable = false, length = 80)
    private String templateVersionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ReportRunStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "run_mode", nullable = false, length = 40)
    private ReportRunMode runMode;

    @Column(name = "queued_at", nullable = false)
    private Instant queuedAt;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "failed_at", nullable = true)
    private Instant failedAt;

    @Column(name = "failure_reason", nullable = true, length = 2000)
    private String failureReason;

    @Column(name = "record_count", nullable = true)
    private Long recordCount;

    @Column(name = "output_count", nullable = true)
    private Long outputCount;

    @Column(name = "execution_duration_ms", nullable = true)
    private Long executionDurationMs;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportRunJpaEntity() {
            // Required by JPA.
        }

        public ReportRunJpaEntity(
                String id,
            String reportRequestId,
            String reportDefinitionId,
            String templateVersionId,
            ReportRunStatus status,
            ReportRunMode runMode,
            Instant queuedAt,
            Instant startedAt,
            Instant completedAt,
            Instant failedAt,
            String failureReason,
            Long recordCount,
            Long outputCount,
            Long executionDurationMs,
            String correlationId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportRequestId = reportRequestId;
        this.reportDefinitionId = reportDefinitionId;
        this.templateVersionId = templateVersionId;
        this.status = status;
        this.runMode = runMode;
        this.queuedAt = queuedAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.failedAt = failedAt;
        this.failureReason = failureReason;
        this.recordCount = recordCount;
        this.outputCount = outputCount;
        this.executionDurationMs = executionDurationMs;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportRequestId() {
        return reportRequestId;
    }


    public String reportDefinitionId() {
        return reportDefinitionId;
    }


    public String templateVersionId() {
        return templateVersionId;
    }


    public ReportRunStatus status() {
        return status;
    }


    public ReportRunMode runMode() {
        return runMode;
    }


    public Instant queuedAt() {
        return queuedAt;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public Instant failedAt() {
        return failedAt;
    }


    public String failureReason() {
        return failureReason;
    }


    public Long recordCount() {
        return recordCount;
    }


    public Long outputCount() {
        return outputCount;
    }


    public Long executionDurationMs() {
        return executionDurationMs;
    }


    public String correlationId() {
        return correlationId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
