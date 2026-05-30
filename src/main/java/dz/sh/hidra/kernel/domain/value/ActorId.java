/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ActorId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Generic actor reference without user or employee business meaning.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.UUID;

public record ActorId(String value) implements ValueObject {

    public ActorId {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("ActorId must not be blank.");
        }
        value = value.trim();
    }

    public static ActorId of(String value) {
        return new ActorId(value);
    }

    public static ActorId newId() {
        return new ActorId(UUID.randomUUID().toString());
    }
}
