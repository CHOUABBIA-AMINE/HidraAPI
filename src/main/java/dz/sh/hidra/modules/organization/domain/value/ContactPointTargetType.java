/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ContactPointTargetType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Governs Organization-owned subject categories that may own operational contact points.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;

import java.util.Locale;

/**
 * Defines the Organization-owned subjects that may own an operational contact point.
 *
 * <p>Business role: contact information may belong to an operational employee or an
 * organization unit. Positions, identity users, topology assets, and external parties
 * are intentionally excluded from this Organization-owned contract.</p>
 *
 * <p>Architecture role: replaces arbitrary textual target discriminators without
 * importing another aggregate class or another bounded context.</p>
 *
 * <p>Validation: textual compatibility input is normalized and accepted only when it
 * maps to one of the governed enum constants.</p>
 *
 * <p>Usage: combine this type with a native Organization identifier through
 * {@link ContactPointTargetReference}. Referenced-object existence is validated in
 * the application layer.</p>
 */
public enum ContactPointTargetType {

    EMPLOYEE,
    ORGANIZATION_UNIT;

    /**
     * Parses a textual target discriminator at a compatibility boundary.
     *
     * @param value textual target type
     * @return governed target type
     */
    public static ContactPointTargetType from(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidOrganizationValueException(
                    "Contact-point target type must not be null or blank."
            );
        }

        try {
            return valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new InvalidOrganizationValueException(
                    "Unsupported contact-point target type: " + value
            );
        }
    }
}
