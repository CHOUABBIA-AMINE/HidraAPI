/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Shift
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Multilingual organization shift definition.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.ShiftType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationCode;
import java.time.Instant;

/**
 * Defines an organization work shift with embedded Arabic, French, and English names.
 *
 * <p>Business role: identifies a reusable work-shift definition referenced by shift assignments.
 *
 * <p>Architecture role: domain model whose localized display names are stored directly on the shift entity.
 *
 * <p>Validation: textual values are normalized by trimming blanks to {@code null}; scheduling policy validation
 * remains outside this storage-focused correction.
 *
 * <p>Usage: callers use {@code nameAr/nameFr/nameEn}; the persistence adapter maintains the legacy database
 * {@code name} column only as a transitional compatibility field.
 *
 * @param id shift identifier
 * @param code language-neutral business code
 * @param nameAr Arabic shift name
 * @param nameFr French shift name
 * @param nameEn English shift name
 * @param shiftType shift classification
 * @param startTime configured start time
 * @param endTime configured end time
 * @param timezone configured timezone
 * @param active whether the shift is active
 * @param createdAt creation timestamp
 * @param updatedAt last update timestamp
 */
public record Shift(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        ShiftType shiftType,
        String startTime,
        String endTime,
        String timezone,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public Shift {
        id = requireText(id, "Shift ID is required.");
        code = OrganizationCode.of(code).value();
        if (shiftType == null) {
            throw new InvalidOrganizationValueException("Shift type is required.");
        }
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        startTime = normalize(startTime);
        endTime = normalize(endTime);
        timezone = normalize(timezone);
    }
    private static String requireText(String value, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new InvalidOrganizationValueException(message);
        }
        return normalized;
    }


    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
