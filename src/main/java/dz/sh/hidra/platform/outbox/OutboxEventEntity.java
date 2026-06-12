/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OutboxEventEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.outbox
 *
 * @Description : Represents the platform-owned technical outbox event source model.
 *
 */
package dz.sh.hidra.platform.outbox;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Platform-owned technical outbox event model.
 *
 * <p>This class is skeleton-aligned and framework-neutral. JPA annotations,
 * if required by the concrete repository, belong to the persistence adapter layer.</p>
 */
public class OutboxEventEntity {

    public static final String TABLE_NAME = "hidra_platform_outbox_event";

    private UUID id;
    private String eventId;
    private String eventType;
    private String aggregateId;
    private String aggregateType;
    private String payload;
    private Instant occurredAt;
    private OutboxEventStatus status;
    private int retryCount;
    private String lastError;
    private Instant createdAt;
    private Instant publishedAt;

    protected OutboxEventEntity() {
        // Required by persistence frameworks.
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
            Instant publishedAt
    ) {
        this.id = Objects.requireNonNull(id, "Outbox event ID must not be null.");
        this.eventId = requireText(eventId, "Outbox event domain event ID must not be null or blank.");
        this.eventType = requireText(eventType, "Outbox event type must not be null or blank.");
        this.aggregateId = normalize(aggregateId);
        this.aggregateType = normalize(aggregateType);
        this.payload = requireText(payload, "Outbox event payload must not be null or blank.");
        this.occurredAt = Objects.requireNonNull(occurredAt, "Outbox event occurrence timestamp must not be null.");
        this.status = Objects.requireNonNull(status, "Outbox event status must not be null.");
        this.retryCount = Math.max(0, retryCount);
        this.lastError = normalize(lastError);
        this.createdAt = Objects.requireNonNull(createdAt, "Outbox event creation timestamp must not be null.");
        this.publishedAt = publishedAt;
    }

    public static OutboxEventEntity pending(
            SerializedDomainEvent serializedEvent,
            String aggregateType,
            String aggregateId
    ) {
        Objects.requireNonNull(serializedEvent, "Serialized domain event must not be null.");
        return new OutboxEventEntity(
                UUID.randomUUID(),
                serializedEvent.eventId(),
                serializedEvent.eventType(),
                aggregateId,
                aggregateType,
                serializedEvent.payload(),
                serializedEvent.occurredAt(),
                OutboxEventStatus.PENDING,
                0,
                null,
                Instant.now(),
                null
        );
    }

    public void markPublished(Instant publicationTime) {
        this.status = OutboxEventStatus.PUBLISHED;
        this.publishedAt = Objects.requireNonNull(publicationTime, "Publication time must not be null.");
        this.lastError = null;
    }

    public void markFailed(String errorMessage) {
        this.status = OutboxEventStatus.FAILED;
        this.retryCount++;
        this.lastError = normalize(errorMessage);
    }

    public void markPendingForRetry() {
        if (status == OutboxEventStatus.FAILED) {
            this.status = OutboxEventStatus.PENDING;
        }
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

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
