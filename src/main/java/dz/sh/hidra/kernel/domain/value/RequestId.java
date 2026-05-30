/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RequestId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Identifier for one request execution.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.UUID;

public record RequestId(String value) implements ValueObject {

    public RequestId {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("RequestId must not be blank.");
        }
        value = value.trim();
    }

    public static RequestId of(String value) {
        return new RequestId(value);
    }

    public static RequestId newId() {
        return new RequestId(UUID.randomUUID().toString());
    }
}
