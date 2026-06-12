/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQuarantineRecordJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetryQuarantineRecord.
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
     * Database-backed JPA entity for TelemetryQuarantineRecord.
     */
    @Entity
    @Table(name = "hidra_telemetry_quarantine_record")
    public class TelemetryQuarantineRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "source_id", nullable = false, length = 80)
    private String sourceId;

    @Column(name = "endpoint_id", nullable = true, length = 80)
    private String endpointId;

    @Column(name = "ingestion_batch_id", nullable = true, length = 80)
    private String ingestionBatchId;

    @Column(name = "external_tag_name", nullable = true, length = 500)
    private String externalTagName;

    @Column(name = "source_timestamp", nullable = true)
    private Instant sourceTimestamp;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    @Column(name = "reason_code", nullable = false, length = 80)
    private String reasonCode;

    @Column(name = "reason_message", nullable = true, columnDefinition = "text")
    private String reasonMessage;

    @Column(name = "raw_payload", nullable = true, columnDefinition = "jsonb")
    private String rawPayload;

    @Column(name = "raw_payload_hash", nullable = true, length = 160)
    private String rawPayloadHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private QuarantineStatus status;

    @Column(name = "resolved_reading_id", nullable = true, length = 80)
    private String resolvedReadingId;

    @Column(name = "resolved_at", nullable = true)
    private Instant resolvedAt;

    @Column(name = "resolved_by_actor_id", nullable = true, length = 80)
    private String resolvedByActorId;

        protected TelemetryQuarantineRecordJpaEntity() {
            // Required by JPA.
        }

        public TelemetryQuarantineRecordJpaEntity(
                String id,
            String sourceId,
            String endpointId,
            String ingestionBatchId,
            String externalTagName,
            Instant sourceTimestamp,
            Instant receivedAt,
            String reasonCode,
            String reasonMessage,
            String rawPayload,
            String rawPayloadHash,
            QuarantineStatus status,
            String resolvedReadingId,
            Instant resolvedAt,
            String resolvedByActorId
        ) {
            this.id = id;
        this.sourceId = sourceId;
        this.endpointId = endpointId;
        this.ingestionBatchId = ingestionBatchId;
        this.externalTagName = externalTagName;
        this.sourceTimestamp = sourceTimestamp;
        this.receivedAt = receivedAt;
        this.reasonCode = reasonCode;
        this.reasonMessage = reasonMessage;
        this.rawPayload = rawPayload;
        this.rawPayloadHash = rawPayloadHash;
        this.status = status;
        this.resolvedReadingId = resolvedReadingId;
        this.resolvedAt = resolvedAt;
        this.resolvedByActorId = resolvedByActorId;
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


    public String ingestionBatchId() {
        return ingestionBatchId;
    }


    public String externalTagName() {
        return externalTagName;
    }


    public Instant sourceTimestamp() {
        return sourceTimestamp;
    }


    public Instant receivedAt() {
        return receivedAt;
    }


    public String reasonCode() {
        return reasonCode;
    }


    public String reasonMessage() {
        return reasonMessage;
    }


    public String rawPayload() {
        return rawPayload;
    }


    public String rawPayloadHash() {
        return rawPayloadHash;
    }


    public QuarantineStatus status() {
        return status;
    }


    public String resolvedReadingId() {
        return resolvedReadingId;
    }


    public Instant resolvedAt() {
        return resolvedAt;
    }


    public String resolvedByActorId() {
        return resolvedByActorId;
    }

    }
