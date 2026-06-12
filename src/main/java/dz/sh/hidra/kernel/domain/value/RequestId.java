/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RequestId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Represents an immutable generic RequestId value object.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.kernel.exception.InvalidValueObjectException;

import java.util.UUID;

/**
 * Identifies one incoming request or operation attempt.
 *
 * @param value non-blank identifier value
 */
public record RequestId(String value) implements ValueObject {

    public RequestId {
        value = requireText(value, "RequestId must not be null or blank.");
    }

    /**
     * Restores an existing identifier.
     *
     * @param value identifier text
     * @return RequestId instance
     */
    public static RequestId of(String value) {
        return new RequestId(value);
    }

    /**
     * Generates a UUID-based identifier.
     *
     * @return new RequestId instance
     */
    public static RequestId newId() {
        return new RequestId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(message);
        }
        return value.trim();
    }
}
