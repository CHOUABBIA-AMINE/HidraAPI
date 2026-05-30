/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainEventId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.event
 *
 * @Description : Immutable identifier for generic domain events.
 *
 */
package dz.sh.hidra.kernel.domain.event;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.UUID;

public record DomainEventId(String value) implements ValueObject {

    public DomainEventId {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("DomainEventId must not be blank.");
        }
        value = value.trim();
    }

    public static DomainEventId of(String value) {
        return new DomainEventId(value);
    }

    public static DomainEventId newId() {
        return new DomainEventId(UUID.randomUUID().toString());
    }
}
