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
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;

/**
 * Domain event raised when an organization unit is created.
 *
 * @param organizationUnitId created organization unit identifier
 * @param organizationUnitCode created organization unit business code
 * @param organizationUnitType created organization unit type catalog reference
 */
public record OrganizationUnitCreatedEvent(
        DomainEventId eventId,
        Instant occurredAt,
        OrganizationUnitId organizationUnitId,
        OrganizationUnitCode organizationUnitCode,
        OrganizationUnitTypeReference organizationUnitType) implements DomainEvent {

    public static final String TYPE = "organization.unit.created";

    public OrganizationUnitCreatedEvent {
        Objects.requireNonNull(eventId, "eventId must not be null.");
        Objects.requireNonNull(occurredAt, "occurredAt must not be null.");
        Objects.requireNonNull(organizationUnitId, "organizationUnitId must not be null.");
        Objects.requireNonNull(organizationUnitCode, "organizationUnitCode must not be null.");
        Objects.requireNonNull(organizationUnitType, "organizationUnitType must not be null.");
    }

    public static OrganizationUnitCreatedEvent occurred(
            OrganizationUnitId organizationUnitId,
            OrganizationUnitCode organizationUnitCode,
            OrganizationUnitTypeReference organizationUnitType) {

        return new OrganizationUnitCreatedEvent(
                DomainEventId.newId(),
                Instant.now(),
                organizationUnitId,
                organizationUnitCode,
                organizationUnitType);
    }

    /**
     * @deprecated use {@link #occurred(OrganizationUnitId, OrganizationUnitCode, OrganizationUnitTypeReference)}
     */
    @Deprecated(forRemoval = false)
    public static OrganizationUnitCreatedEvent occurred(
            OrganizationUnitId organizationUnitId,
            OrganizationUnitCode organizationUnitCode,
            OrganizationUnitType organizationUnitType) {

        return occurred(organizationUnitId, organizationUnitCode, OrganizationUnitTypeReference.from(organizationUnitType));
    }

    @Override
    public String eventType() {
        return TYPE;
    }
}
