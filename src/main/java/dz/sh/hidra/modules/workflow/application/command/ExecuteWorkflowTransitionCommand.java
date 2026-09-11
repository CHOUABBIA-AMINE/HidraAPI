/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExecuteWorkflowTransitionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.command
 *
 * @Description : Command for authoritative workflow transition execution.
 *
 */
package dz.sh.hidra.modules.workflow.application.command;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public record ExecuteWorkflowTransitionCommand(
        String taskId,
        String transitionId,
        Instant expectedTaskUpdatedAt,
        String reasonId,
        String decisionNote,
        String commentText,
        String correlationId,
        String actorId,
        String actorUsername,
        String actorDisplayName,
        Set<String> effectivePermissions
) {
    public ExecuteWorkflowTransitionCommand {
        taskId = requireText(taskId, "Workflow task ID must not be blank.");
        transitionId = requireText(transitionId, "Workflow transition ID must not be blank.");
        expectedTaskUpdatedAt = Objects.requireNonNull(expectedTaskUpdatedAt, "Expected task update timestamp must not be null.");
        actorId = requireText(actorId, "Workflow actor ID must not be blank.");
        actorUsername = normalize(actorUsername);
        actorDisplayName = requireText(actorDisplayName, "Workflow actor display name must not be blank.");
        reasonId = normalize(reasonId);
        decisionNote = normalize(decisionNote);
        commentText = normalize(commentText);
        correlationId = normalize(correlationId);
        effectivePermissions = effectivePermissions == null ? Set.of() : Set.copyOf(effectivePermissions);
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
