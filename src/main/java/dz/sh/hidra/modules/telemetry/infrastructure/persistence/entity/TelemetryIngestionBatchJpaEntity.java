/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionBatchJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : JPA entity for telemetry ingestion batches.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for telemetry ingestion batches.
 *
 * <p>Architecture role:
 * Persistence-only representation of telemetry data. It must not be exposed to domain, application,
 * or REST layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_telemetry_ingestion_batch")
public class TelemetryIngestionBatchJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "source_id", nullable = false)
    private String sourceId;

    @Column(name = "correlation_id", nullable = true)
    private String correlationId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "received_count", nullable = false)
    private Integer receivedCount;

    @Column(name = "accepted_count", nullable = false)
    private Integer acceptedCount;

    @Column(name = "rejected_count", nullable = false)
    private Integer rejectedCount;

    @Column(name = "duplicate_count", nullable = false)
    private Integer duplicateCount;

    @Column(name = "quarantined_count", nullable = false)
    private Integer quarantinedCount;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "failure_reason", nullable = true)
    private String failureReason;

    protected TelemetryIngestionBatchJpaEntity() {
        // Required by JPA.
    }

    public TelemetryIngestionBatchJpaEntity(
            String id,            String sourceId,            String correlationId,            String status,            Integer receivedCount,            Integer acceptedCount,            Integer rejectedCount,            Integer duplicateCount,            Integer quarantinedCount,            Instant startedAt,            Instant completedAt,            String failureReason) {
        this.id = id;
        this.sourceId = sourceId;
        this.correlationId = correlationId;
        this.status = status;
        this.receivedCount = receivedCount;
        this.acceptedCount = acceptedCount;
        this.rejectedCount = rejectedCount;
        this.duplicateCount = duplicateCount;
        this.quarantinedCount = quarantinedCount;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.failureReason = failureReason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getReceivedCount() {
        return receivedCount;
    }

    public void setReceivedCount(Integer receivedCount) {
        this.receivedCount = receivedCount;
    }

    public Integer getAcceptedCount() {
        return acceptedCount;
    }

    public void setAcceptedCount(Integer acceptedCount) {
        this.acceptedCount = acceptedCount;
    }

    public Integer getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(Integer rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public Integer getDuplicateCount() {
        return duplicateCount;
    }

    public void setDuplicateCount(Integer duplicateCount) {
        this.duplicateCount = duplicateCount;
    }

    public Integer getQuarantinedCount() {
        return quarantinedCount;
    }

    public void setQuarantinedCount(Integer quarantinedCount) {
        this.quarantinedCount = quarantinedCount;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
