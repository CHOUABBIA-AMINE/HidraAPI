/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssigneeType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Governs organization-owned subject types that may receive responsibility assignments.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import java.util.Locale;

/**
 * Defines the only organization subjects that may hold a responsibility assignment.
 *
 * <p>Persistence uses enum names so existing EMPLOYEE and ORGANIZATION_UNIT values remain unchanged.</p>
 */
public enum ResponsibilityAssigneeType {

    EMPLOYEE,
    ORGANIZATION_UNIT;

    public static ResponsibilityAssigneeType from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Responsibility assignee type must not be null or blank.");
        }
        try {
            return valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Unsupported responsibility assignee type: " + value,
                    exception
            );
        }
    }
}
