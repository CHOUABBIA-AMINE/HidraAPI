/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : User lifecycle status enum.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.Locale;

/**
 * Lifecycle status for an identity user.
 *
 * <p>Business role: expresses whether a user is registered, active, suspended, or
 * disabled in the identity access model.</p>
 *
 * <p>Architecture role: immutable enum value used by the user aggregate and application
 * contracts without depending on Spring Security account status types.</p>
 *
 * <p>Validation responsibility: {@link #from(String)} rejects null, blank, or unknown
 * status names.</p>
 *
 * <p>Usage: use enum constants for lifecycle transitions and {@link #from(String)} for
 * validated parsing at boundaries.</p>
 */
public enum UserStatus implements ValueObject {

    REGISTERED,
    ACTIVE,
    SUSPENDED,
    DISABLED;

    public static UserStatus from(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("UserStatus must not be blank.");
        }

        try {
            return UserStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new InvalidValueObjectException("Unsupported UserStatus: " + value + ".", exception);
        }
    }
}
