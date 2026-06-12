/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrelationId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Represents an immutable generic CorrelationId value object.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.kernel.exception.InvalidValueObjectException;

import java.util.UUID;

/**
 * Groups related operations across commands, events, logs, audit, outbox, and API responses.
 *
 * @param value non-blank identifier value
 */
public record CorrelationId(String value) implements ValueObject {

    public CorrelationId {
        value = requireText(value, "CorrelationId must not be null or blank.");
    }

    /**
     * Restores an existing identifier.
     *
     * @param value identifier text
     * @return CorrelationId instance
     */
    public static CorrelationId of(String value) {
        return new CorrelationId(value);
    }

    /**
     * Generates a UUID-based identifier.
     *
     * @return new CorrelationId instance
     */
    public static CorrelationId newId() {
        return new CorrelationId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(message);
        }
        return value.trim();
    }
}
