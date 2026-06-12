/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionBatchJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetryIngestionBatch.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for TelemetryIngestionBatch.
     */
    @Entity
    @Table(name = "hidra_telemetry_ingestion_batch")
    public class TelemetryIngestionBatchJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "source_id", nullable = false, length = 80)
    private String sourceId;

    @Column(name = "endpoint_id", nullable = true, length = 80)
    private String endpointId;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private IngestionBatchStatus status;

    @Column(name = "received_count", nullable = false)
    private int receivedCount;

    @Column(name = "accepted_count", nullable = false)
    private int acceptedCount;

    @Column(name = "rejected_count", nullable = false)
    private int rejectedCount;

    @Column(name = "duplicate_count", nullable = false)
    private int duplicateCount;

    @Column(name = "quarantined_count", nullable = false)
    private int quarantinedCount;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "failure_reason", nullable = true, columnDefinition = "text")
    private String failureReason;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

        protected TelemetryIngestionBatchJpaEntity() {
            // Required by JPA.
        }

        public TelemetryIngestionBatchJpaEntity(
                String id,
            String sourceId,
            String endpointId,
            String correlationId,
            IngestionBatchStatus status,
            int receivedCount,
            int acceptedCount,
            int rejectedCount,
            int duplicateCount,
            int quarantinedCount,
            Instant startedAt,
            Instant completedAt,
            String failureReason,
            String createdByActorId
        ) {
            this.id = id;
        this.sourceId = sourceId;
        this.endpointId = endpointId;
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
        this.createdByActorId = createdByActorId;
        }


    public String id() {
        return id;
    }


    public String sourceId() {
        return sourceId;
    }


    public String endpointId() {
        return endpointId;
    }


    public String correlationId() {
        return correlationId;
    }


    public IngestionBatchStatus status() {
        return status;
    }


    public int receivedCount() {
        return receivedCount;
    }


    public int acceptedCount() {
        return acceptedCount;
    }


    public int rejectedCount() {
        return rejectedCount;
    }


    public int duplicateCount() {
        return duplicateCount;
    }


    public int quarantinedCount() {
        return quarantinedCount;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String failureReason() {
        return failureReason;
    }


    public String createdByActorId() {
        return createdByActorId;
    }

    }
