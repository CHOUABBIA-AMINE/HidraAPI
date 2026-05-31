/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Stable identity role identifier value object.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.UUID;

/**
 * Stable identifier value object.
 *
 * <p>Business role: uniquely identifies a role that groups identity permissions.</p>
 *
 * <p>Architecture role: immutable domain value object used by identity domain models,
 * commands, queries, and repository contracts.</p>
 *
 * <p>Validation responsibility: rejects null or blank values and trims accepted values.</p>
 *
 * <p>Usage: create from an existing value with {@link #of(String)} or create a new
 * identifier with {@link #newId()}.</p>
 */
public record RoleId(String value) implements ValueObject {

    public RoleId {
        value = requireText(value, "RoleId");
    }

    public static RoleId of(String value) {
        return new RoleId(value);
    }

    public static RoleId newId() {
        return new RoleId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be blank.");
        }
        return value.trim();
    }
}
