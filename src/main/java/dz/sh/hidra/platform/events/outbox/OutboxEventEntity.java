/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OutboxEventEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.events.outbox
 *
 * @Description : JPA entity for technical platform outbox event storage.
 *
 */
package dz.sh.hidra.platform.events.outbox;

import dz.sh.hidra.platform.events.serialization.SerializedDomainEvent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "hidra_platform_outbox_event")
public class OutboxEventEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "event_id", nullable = false, updatable = false, length = 120)
    private String eventId;

    @Column(name = "event_type", nullable = false, updatable = false, length = 255)
    private String eventType;

    @Column(name = "aggregate_id", length = 120)
    private String aggregateId;

    @Column(name = "aggregate_type", length = 255)
    private String aggregateType;

    @Lob
    @Column(name = "payload", nullable = false, columnDefinition = "text")
    private String payload;

    @Column(name = "occurred_at", nullable = false, updatable = false)
    private Instant occurredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private OutboxEventStatus status;

    @Column(name = "retry_count", nullable = false)
    private int retryCount;

    @Column(name = "last_error", columnDefinition = "text")
    private String lastError;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "published_at")
    private Instant publishedAt;

    protected OutboxEventEntity() {
    }

    private OutboxEventEntity(
            UUID id,
            String eventId,
            String eventType,
            String aggregateId,
            String aggregateType,
            String payload,
            Instant occurredAt,
            OutboxEventStatus status,
            int retryCount,
            String lastError,
            Instant createdAt,
            Instant publishedAt) {
        this.id = id;
        this.eventId = eventId;
        this.eventType = eventType;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.payload = payload;
        this.occurredAt = occurredAt;
        this.status = status;
        this.retryCount = retryCount;
        this.lastError = lastError;
        this.createdAt = createdAt;
        this.publishedAt = publishedAt;
    }

    public static OutboxEventEntity pending(SerializedDomainEvent event) {
        return pending(event, null, null);
    }

    public static OutboxEventEntity pending(SerializedDomainEvent event, String aggregateId, String aggregateType) {
        if (event == null) {
            throw new IllegalArgumentException("Serialized domain event must not be null.");
        }
        return new OutboxEventEntity(
                UUID.randomUUID(),
                event.eventId(),
                event.eventType(),
                normalize(aggregateId),
                normalize(aggregateType),
                event.payload(),
                event.occurredAt(),
                OutboxEventStatus.PENDING,
                0,
                null,
                Instant.now(),
                null);
    }

    public UUID id() {
        return id;
    }

    public String eventId() {
        return eventId;
    }

    public String eventType() {
        return eventType;
    }

    public String aggregateId() {
        return aggregateId;
    }

    public String aggregateType() {
        return aggregateType;
    }

    public String payload() {
        return payload;
    }

    public Instant occurredAt() {
        return occurredAt;
    }

    public OutboxEventStatus status() {
        return status;
    }

    public int retryCount() {
        return retryCount;
    }

    public String lastError() {
        return lastError;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant publishedAt() {
        return publishedAt;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
