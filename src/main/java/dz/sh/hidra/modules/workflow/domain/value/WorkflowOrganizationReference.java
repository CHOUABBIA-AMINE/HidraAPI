/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowOrganizationReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow organization snapshot reference value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Organization unit snapshot for workflow assignments and decisions.
 *
 * <p>Architecture rule:
 * Workflow stores organization snapshots for traceability but does not own organization data.
 *
 * @param organizationUnitId organization unit identifier
 * @param organizationUnitNameSnapshot organization unit display name snapshot
 * @param roleCodeSnapshot optional role or authority code snapshot
 */
public record WorkflowOrganizationReference(
        String organizationUnitId,
        String organizationUnitNameSnapshot,
        String roleCodeSnapshot) implements ValueObject {

    public WorkflowOrganizationReference {
        organizationUnitId = requireText(organizationUnitId, "WorkflowOrganizationReference organizationUnitId", 80);
        organizationUnitNameSnapshot = requireText(organizationUnitNameSnapshot, "WorkflowOrganizationReference organizationUnitNameSnapshot", 160);
        roleCodeSnapshot = normalizeOptional(roleCodeSnapshot, "WorkflowOrganizationReference roleCodeSnapshot", 80);
    }

    public static WorkflowOrganizationReference of(
            String organizationUnitId,
            String organizationUnitNameSnapshot,
            String roleCodeSnapshot) {

        return new WorkflowOrganizationReference(
                organizationUnitId,
                organizationUnitNameSnapshot,
                roleCodeSnapshot);
    }

    private static String requireText(String value, String fieldName, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > maxLength) {
            throw new InvalidValueObjectException(fieldName + " length must not exceed " + maxLength + " characters.");
        }

        return normalized;
    }

    private static String normalizeOptional(String value, String fieldName, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > maxLength) {
            throw new InvalidValueObjectException(fieldName + " length must not exceed " + maxLength + " characters.");
        }

        return normalized;
    }
}
