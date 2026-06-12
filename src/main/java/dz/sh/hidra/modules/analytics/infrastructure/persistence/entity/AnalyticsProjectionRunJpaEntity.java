/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionRunJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsProjectionRun.
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
     * Database-backed JPA entity for AnalyticsProjectionRun.
     */
    @Entity
    @Table(name = "hidra_analytics_projection_run")
    public class AnalyticsProjectionRunJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "projection_definition_id", nullable = false, length = 80)
    private String projectionDefinitionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "run_status", nullable = false, length = 40)
    private AnalyticsRunStatus runStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "run_mode", nullable = false, length = 40)
    private AnalyticsRunMode runMode;

    @Column(name = "period_start", nullable = true)
    private Instant periodStart;

    @Column(name = "period_end", nullable = true)
    private Instant periodEnd;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "source_watermark", nullable = true, length = 255)
    private String sourceWatermark;

    @Column(name = "records_read", nullable = true)
    private Long recordsRead;

    @Column(name = "records_written", nullable = true)
    private Long recordsWritten;

    @Column(name = "error_code", nullable = true, length = 120)
    private String errorCode;

    @Column(name = "error_message", nullable = true, length = 2000)
    private String errorMessage;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AnalyticsProjectionRunJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsProjectionRunJpaEntity(
                String id,
            String projectionDefinitionId,
            AnalyticsRunStatus runStatus,
            AnalyticsRunMode runMode,
            Instant periodStart,
            Instant periodEnd,
            Instant startedAt,
            Instant completedAt,
            String sourceWatermark,
            Long recordsRead,
            Long recordsWritten,
            String errorCode,
            String errorMessage,
            String correlationId,
            Instant createdAt
        ) {
            this.id = id;
        this.projectionDefinitionId = projectionDefinitionId;
        this.runStatus = runStatus;
        this.runMode = runMode;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.sourceWatermark = sourceWatermark;
        this.recordsRead = recordsRead;
        this.recordsWritten = recordsWritten;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String projectionDefinitionId() {
        return projectionDefinitionId;
    }


    public AnalyticsRunStatus runStatus() {
        return runStatus;
    }


    public AnalyticsRunMode runMode() {
        return runMode;
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


    public String sourceWatermark() {
        return sourceWatermark;
    }


    public Long recordsRead() {
        return recordsRead;
    }


    public Long recordsWritten() {
        return recordsWritten;
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
