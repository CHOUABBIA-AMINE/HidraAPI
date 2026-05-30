/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationScopeId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Generic organization or operational scope reference.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.UUID;

public record OrganizationScopeId(String value) implements ValueObject {

    public OrganizationScopeId {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("OrganizationScopeId must not be blank.");
        }
        value = value.trim();
    }

    public static OrganizationScopeId of(String value) {
        return new OrganizationScopeId(value);
    }

    public static OrganizationScopeId newId() {
        return new OrganizationScopeId(UUID.randomUUID().toString());
    }
}
