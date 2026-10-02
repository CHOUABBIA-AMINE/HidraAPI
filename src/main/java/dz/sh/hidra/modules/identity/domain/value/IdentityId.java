/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Represents a stable identity module identifier.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;

import java.util.UUID;

/**
 * Stable identity module identifier.
 *
 * @param value identifier value
 */
public record IdentityId(String value) {

    public IdentityId {
        value = requireText(value, "Identity ID must not be null or blank.");
    }

    public static IdentityId of(String value) {
        return new IdentityId(value);
    }

    public static IdentityId newId() {
        return new IdentityId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidIdentityValueException(message);
        }
        return value.trim();
    }
}
