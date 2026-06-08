/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Neutral workflow target reference value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Neutral reference to the object controlled by a workflow instance.
 *
 * <p>Architecture rule:
 * Workflow references external module objects by stable identifiers and snapshots. It must not import
 * telemetry, topology, planning, monitoring, incident, risk, analytics, or audit domain objects.
 *
 * @param targetModule owning module code, for v1 usually telemetry
 * @param targetType target type catalog reference, for v1 usually TELEMETRY_READING
 * @param targetId external target identifier
 * @param targetCodeSnapshot optional target code snapshot
 * @param targetLabelSnapshot optional target label snapshot
 */
public record WorkflowTargetReference(
        String targetModule,
        WorkflowTargetTypeReference targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot) implements ValueObject {

    public WorkflowTargetReference {
        targetModule = requireText(targetModule, "WorkflowTargetReference targetModule");
        targetType = Objects.requireNonNull(targetType, "WorkflowTargetReference targetType must not be null.");
        targetId = requireText(targetId, "WorkflowTargetReference targetId");
        targetCodeSnapshot = normalizeOptional(targetCodeSnapshot, "WorkflowTargetReference targetCodeSnapshot", 120);
        targetLabelSnapshot = normalizeOptional(targetLabelSnapshot, "WorkflowTargetReference targetLabelSnapshot", 240);
    }

    public static WorkflowTargetReference of(
            String targetModule,
            WorkflowTargetTypeReference targetType,
            String targetId,
            String targetCodeSnapshot,
            String targetLabelSnapshot) {

        return new WorkflowTargetReference(
                targetModule,
                targetType,
                targetId,
                targetCodeSnapshot,
                targetLabelSnapshot);
    }

    public boolean isTelemetryReading() {
        return "telemetry".equals(targetModule) && targetType.is("TELEMETRY_READING");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 120) {
            throw new InvalidValueObjectException(fieldName + " length must not exceed 120 characters.");
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
