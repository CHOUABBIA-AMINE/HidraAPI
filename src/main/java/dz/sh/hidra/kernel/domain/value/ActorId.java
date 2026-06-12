/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ActorId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Represents an immutable generic ActorId value object.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.kernel.exception.InvalidValueObjectException;

import java.util.UUID;

/**
 * Represents a generic actor reference without user, employee, service, or party business meaning.
 *
 * @param value non-blank identifier value
 */
public record ActorId(String value) implements ValueObject {

    public ActorId {
        value = requireText(value, "ActorId must not be null or blank.");
    }

    /**
     * Restores an existing identifier.
     *
     * @param value identifier text
     * @return ActorId instance
     */
    public static ActorId of(String value) {
        return new ActorId(value);
    }

    /**
     * Generates a UUID-based identifier.
     *
     * @return new ActorId instance
     */
    public static ActorId newId() {
        return new ActorId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(message);
        }
        return value.trim();
    }
}
