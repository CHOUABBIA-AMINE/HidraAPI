/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Role lifecycle status enum.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.Locale;

/**
 * Lifecycle status for an identity role.
 *
 * <p>Business role: expresses whether a role is available for assignment or disabled in
 * the identity access model.</p>
 *
 * <p>Architecture role: immutable enum value used by the role aggregate and application
 * contracts without depending on platform security classes.</p>
 *
 * <p>Validation responsibility: {@link #from(String)} rejects null, blank, or unknown
 * status names.</p>
 *
 * <p>Usage: use enum constants for role lifecycle decisions and {@link #from(String)}
 * for validated parsing at boundaries.</p>
 */
public enum RoleStatus implements ValueObject {

    ACTIVE,
    DISABLED;

    public static RoleStatus from(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("RoleStatus must not be blank.");
        }

        try {
            return RoleStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new InvalidValueObjectException("Unsupported RoleStatus: " + value + ".", exception);
        }
    }
}
