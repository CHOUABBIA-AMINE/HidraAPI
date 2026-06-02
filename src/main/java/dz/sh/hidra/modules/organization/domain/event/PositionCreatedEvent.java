/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.event
 *
 * @Description : Domain event raised when an organization position is created.
 *
 */
package dz.sh.hidra.modules.organization.domain.event;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import dz.sh.hidra.modules.organization.domain.value.PositionCode;
import dz.sh.hidra.modules.organization.domain.value.PositionId;

/**
 * Domain event raised when an organization position is created.
 *
 * <p>Business role:
 * Captures the business fact that an operational position or function was created, such as Station Team Leader, Station Boss, Region Director, or Department Chief. It is not an identity role.
 *
 * <p>Architecture role:
 * This is an immutable organization domain event. It implements the kernel domain event contract
 * and must not depend on platform event infrastructure, persistence entities, REST DTOs, identity, topology,
 * Spring, JPA, or infrastructure code.
 *
 * <p>Validation:
 * Event id, occurrence instant, position id, and position code are required.
 *
 * <p>Usage:
 * Raise this event from organization aggregate or application behavior when the related business
 * fact occurs. Keep payloads limited to safe identifiers, codes, and non-secret business context.
 *
 * @param positionId created position identifier
 * @param positionCode created position business code
 */
public record PositionCreatedEvent(
        DomainEventId eventId,
        Instant occurredAt,
        PositionId positionId,
        PositionCode positionCode) implements DomainEvent {

    /**
     * Event type emitted for this business fact.
     */
    public static final String TYPE = "organization.position.created";

    public PositionCreatedEvent {
        Objects.requireNonNull(eventId, "eventId must not be null.");
        Objects.requireNonNull(occurredAt, "occurredAt must not be null.");
        Objects.requireNonNull(positionId, "positionId must not be null.");
        Objects.requireNonNull(positionCode, "positionCode must not be null.");
    }

    /**
     * Creates a new domain event with generated event id and current occurrence instant.
     *
     * @param positionId created position identifier
     * @param positionCode created position business code
     * @return created domain event
     */
    public static PositionCreatedEvent occurred(
            PositionId positionId,
            PositionCode positionCode) {

        return new PositionCreatedEvent(
                DomainEventId.newId(),
                Instant.now(),
                positionId,
                positionCode);
    }

    @Override
    public String eventType() {
        return TYPE;
    }
}
