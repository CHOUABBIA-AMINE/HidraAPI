/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionRunJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
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

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunMode;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "hidra_analytics_projection_run")
public class AnalyticsProjectionRunJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "projection_definition_id", nullable = false, length = 80)
    private String projectionDefinitionId;

    @Column(name = "projection_definition_version", nullable = false, length = 120)
    private String projectionDefinitionVersion;

    @Enumerated(EnumType.STRING)
    @Column(name = "run_status", nullable = false, length = 40)
    private AnalyticsRunStatus runStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "run_mode", nullable = false, length = 40)
    private AnalyticsRunMode runMode;

    @Column(name = "period_start")
    private Instant periodStart;

    @Column(name = "period_end")
    private Instant periodEnd;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "source_watermark", length = 255)
    private String sourceWatermark;

    @Column(name = "records_read")
    private Long recordsRead;

    @Column(name = "records_written")
    private Long recordsWritten;

    @Column(name = "error_code", length = 120)
    private String errorCode;

    @Column(name = "error_message", length = 2000)
    private String errorMessage;

    @Column(name = "correlation_id", length = 120)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected AnalyticsProjectionRunJpaEntity() { }

    public AnalyticsProjectionRunJpaEntity(
            String id,
            String projectionDefinitionId,
            String projectionDefinitionVersion,
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
        this.projectionDefinitionVersion = projectionDefinitionVersion;
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

    public String id() { return id; }
    public String projectionDefinitionId() { return projectionDefinitionId; }
    public String projectionDefinitionVersion() { return projectionDefinitionVersion; }
    public AnalyticsRunStatus runStatus() { return runStatus; }
    public AnalyticsRunMode runMode() { return runMode; }
    public Instant periodStart() { return periodStart; }
    public Instant periodEnd() { return periodEnd; }
    public Instant startedAt() { return startedAt; }
    public Instant completedAt() { return completedAt; }
    public String sourceWatermark() { return sourceWatermark; }
    public Long recordsRead() { return recordsRead; }
    public Long recordsWritten() { return recordsWritten; }
    public String errorCode() { return errorCode; }
    public String errorMessage() { return errorMessage; }
    public String correlationId() { return correlationId; }
    public Instant createdAt() { return createdAt; }
}
