/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingSubjectType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Governs Organization-owned subject categories that may participate in reporting lines.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;

import java.util.Locale;

/**
 * Defines the Organization-owned subjects that can be a source or target of a reporting line.
 *
 * <p>Business role: reporting relationships may connect employees, positions, or
 * organization units. No identity user, topology asset, or other-module object is a
 * reporting subject in the Organization bounded context.</p>
 *
 * <p>Architecture role: this domain enum replaces arbitrary textual reporting
 * discriminators while remaining independent of persistence and other modules.</p>
 *
 * <p>Validation: {@link #from(String)} accepts only the three governed enum names
 * after trim/case normalization and rejects blank or unsupported values.</p>
 *
 * <p>Usage: use this type only to classify a reporting subject. Pair it with the
 * subject identifier through {@link ReportingSubjectReference}.</p>
 */
public enum ReportingSubjectType {

    EMPLOYEE,
    POSITION,
    ORGANIZATION_UNIT;

    /**
     * Parses a textual discriminator at a compatibility boundary.
     *
     * @param value textual subject type
     * @return governed reporting subject type
     */
    public static ReportingSubjectType from(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidOrganizationValueException(
                    "Reporting subject type must not be null or blank."
            );
        }

        try {
            return valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new InvalidOrganizationValueException(
                    "Unsupported reporting subject type: " + value
            );
        }
    }
}
