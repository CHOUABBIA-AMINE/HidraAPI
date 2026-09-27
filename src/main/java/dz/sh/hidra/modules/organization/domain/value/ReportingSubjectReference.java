/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingSubjectReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Typed reference to an Organization-owned employee, position, or organization-unit reporting subject.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;

/**
 * Identifies one Organization-owned subject participating in a reporting relationship.
 *
 * <p>Business role: binds a governed reporting subject category to the native
 * identifier of the employee, position, or organization unit that participates in a
 * reporting line.</p>
 *
 * <p>Architecture role: this value object keeps reporting-line domain state typed
 * without importing another aggregate class or any external module.</p>
 *
 * <p>Validation: both subject type and target identifier are mandatory. Existence,
 * lifecycle status, cycle detection, and reporting-policy validation belong to
 * application/domain policies that can consult repositories.</p>
 *
 * <p>Usage: use as the canonical source/target identity inside {@code ReportingLine}.
 * Textual type/id pairs are accepted only through explicit compatibility factories.</p>
 *
 * @param type governed Organization reporting subject category
 * @param targetId native identifier of the referenced Organization subject
 */
public record ReportingSubjectReference(
        ReportingSubjectType type,
        String targetId
) {

    public ReportingSubjectReference {
        if (type == null) {
            throw new InvalidOrganizationValueException(
                    "Reporting subject type is required."
            );
        }

        if (targetId == null || targetId.isBlank()) {
            throw new InvalidOrganizationValueException(
                    "Reporting subject target ID is required."
            );
        }

        targetId = targetId.trim();
    }

    /**
     * Builds a typed reference from a legacy textual discriminator.
     *
     * @param type textual reporting subject type
     * @param targetId native subject identifier
     * @return validated typed reporting reference
     */
    public static ReportingSubjectReference from(String type, String targetId) {
        return new ReportingSubjectReference(ReportingSubjectType.from(type), targetId);
    }
}
