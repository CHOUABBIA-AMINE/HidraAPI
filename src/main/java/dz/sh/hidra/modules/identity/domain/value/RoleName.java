/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Validated role display name value object.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Human-readable display name for an identity role.
 *
 * <p>Business role: names a role in a way operators and administrators can understand.</p>
 *
 * <p>Architecture role: immutable domain value object used by the role aggregate and
 * application DTOs.</p>
 *
 * <p>Validation responsibility: rejects null, blank, too-short, or too-long display
 * names and trims accepted values.</p>
 *
 * <p>Usage: create with {@link #of(String)} when creating or renaming an identity role.</p>
 */
public record RoleName(String value) implements ValueObject {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 100;

    public RoleName {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("RoleName must not be blank.");
        }

        value = value.trim();

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidValueObjectException("RoleName must contain between 3 and 100 characters.");
        }
    }

    public static RoleName of(String value) {
        return new RoleName(value);
    }
}
