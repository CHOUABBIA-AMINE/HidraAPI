/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Validated permission display name value object.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Human-readable display name for a permission catalog entry.
 *
 * <p>Business role: names a permission in a way administrators can understand when
 * assigning access to roles.</p>
 *
 * <p>Architecture role: immutable domain value object used by permission models and
 * application DTOs.</p>
 *
 * <p>Validation responsibility: rejects null, blank, too-short, or too-long display
 * names and trims accepted values.</p>
 *
 * <p>Usage: create with {@link #of(String)} when defining permission catalog entries.</p>
 */
public record PermissionName(String value) implements ValueObject {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 120;

    public PermissionName {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("PermissionName must not be blank.");
        }

        value = value.trim();

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidValueObjectException("PermissionName must contain between 3 and 120 characters.");
        }
    }

    public static PermissionName of(String value) {
        return new PermissionName(value);
    }
}
