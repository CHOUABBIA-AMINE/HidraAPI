/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActorReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow actor snapshot reference value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Actor snapshot for workflow actions and assignments.
 *
 * <p>Architecture rule:
 * Workflow stores actor snapshots for traceability but does not own identity data.
 *
 * @param actorId identity actor identifier
 * @param actorUsernameSnapshot optional username snapshot
 * @param actorDisplayNameSnapshot display name snapshot
 * @param roleCodeSnapshot optional role code snapshot
 */
public record WorkflowActorReference(
        String actorId,
        String actorUsernameSnapshot,
        String actorDisplayNameSnapshot,
        String roleCodeSnapshot) implements ValueObject {

    public WorkflowActorReference {
        actorId = requireText(actorId, "WorkflowActorReference actorId", 80);
        actorUsernameSnapshot = normalizeOptional(actorUsernameSnapshot, "WorkflowActorReference actorUsernameSnapshot", 120);
        actorDisplayNameSnapshot = requireText(actorDisplayNameSnapshot, "WorkflowActorReference actorDisplayNameSnapshot", 160);
        roleCodeSnapshot = normalizeOptional(roleCodeSnapshot, "WorkflowActorReference roleCodeSnapshot", 80);
    }

    public static WorkflowActorReference of(
            String actorId,
            String actorUsernameSnapshot,
            String actorDisplayNameSnapshot,
            String roleCodeSnapshot) {

        return new WorkflowActorReference(
                actorId,
                actorUsernameSnapshot,
                actorDisplayNameSnapshot,
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
