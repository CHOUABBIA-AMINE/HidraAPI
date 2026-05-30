/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrelationId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Cross-request correlation identifier for tracing related operations.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.UUID;

public record CorrelationId(String value) implements ValueObject {

    public CorrelationId {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("CorrelationId must not be blank.");
        }
        value = value.trim();
    }

    public static CorrelationId of(String value) {
        return new CorrelationId(value);
    }

    public static CorrelationId newId() {
        return new CorrelationId(UUID.randomUUID().toString());
    }
}
