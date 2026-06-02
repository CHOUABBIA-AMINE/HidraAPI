/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.event
 *
 * @Description : Domain event raised when an organization unit is created.
 *
 */
package dz.sh.hidra.modules.organization.domain.event;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitType;

/**
 * Domain event raised when an organization unit is created.
 *
 * <p>Business role:
 * Captures the business fact that an organization unit was created, including station-as-organization-unit structures that represent people and responsibility rather than topology assets.
 *
 * <p>Architecture role:
 * This is an immutable organization domain event. It implements the kernel domain event contract
 * and must not depend on platform event infrastructure, persistence entities, REST DTOs, identity, topology,
 * Spring, JPA, or infrastructure code.
 *
 * <p>Validation:
 * Event id, occurrence instant, organization unit id, code, and type are required.
 *
 * <p>Usage:
 * Raise this event from organization aggregate or application behavior when the related business
 * fact occurs. Keep payloads limited to safe identifiers, codes, and non-secret business context.
 *
 * @param organizationUnitId created organization unit identifier
 * @param organizationUnitCode created organization unit business code
 * @param organizationUnitType created organization unit type
 */
public record OrganizationUnitCreatedEvent(
        DomainEventId eventId,
        Instant occurredAt,
        OrganizationUnitId organizationUnitId,
        OrganizationUnitCode organizationUnitCode,
        OrganizationUnitType organizationUnitType) implements DomainEvent {

    /**
     * Event type emitted for this business fact.
     */
    public static final String TYPE = "organization.unit.created";

    public OrganizationUnitCreatedEvent {
        Objects.requireNonNull(eventId, "eventId must not be null.");
        Objects.requireNonNull(occurredAt, "occurredAt must not be null.");
        Objects.requireNonNull(organizationUnitId, "organizationUnitId must not be null.");
        Objects.requireNonNull(organizationUnitCode, "organizationUnitCode must not be null.");
        Objects.requireNonNull(organizationUnitType, "organizationUnitType must not be null.");
    }

    /**
     * Creates a new domain event with generated event id and current occurrence instant.
     *
     * @param organizationUnitId created organization unit identifier
     * @param organizationUnitCode created organization unit business code
     * @param organizationUnitType created organization unit type
     * @return created domain event
     */
    public static OrganizationUnitCreatedEvent occurred(
            OrganizationUnitId organizationUnitId,
            OrganizationUnitCode organizationUnitCode,
            OrganizationUnitType organizationUnitType) {

        return new OrganizationUnitCreatedEvent(
                DomainEventId.newId(),
                Instant.now(),
                organizationUnitId,
                organizationUnitCode,
                organizationUnitType);
    }

    @Override
    public String eventType() {
        return TYPE;
    }
}
