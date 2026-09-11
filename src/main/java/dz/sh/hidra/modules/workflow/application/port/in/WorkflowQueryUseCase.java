/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.in
 *
 * @Description : Provides authenticated workflow task inbox, instance history, and backend transition metadata.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.in;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface WorkflowQueryUseCase {

    Page<TaskView> tasks(String actorReference, String view, int page, int size);

    TaskView task(String id);

    InstanceView instance(String id);

    List<TimelineEntry> timeline(String instanceId);

    List<AvailableActionView> availableActions(String taskId, String actorReference, Set<String> effectivePermissions);

    record Page<T>(List<T> content, int page, int size, long totalElements, int totalPages, boolean hasNext) { }

    record TaskView(
            String id,
            String instanceId,
            String stepId,
            String status,
            String assignedActorId,
            String assignedActorUsername,
            String assignedActorDisplayName,
            String assignedOrganizationUnitId,
            String assignedOrganizationUnitName,
            String assignedRoleCode,
            String priorityId,
            Instant dueAt,
            String claimedByActorId,
            Instant claimedAt,
            String completedByActorId,
            Instant completedAt,
            String taskLabel,
            String slaStatus,
            Instant escalatedAt,
            Instant delegatedAt,
            Instant expiresAt,
            Instant createdAt,
            Instant updatedAt
    ) { }

    record InstanceView(
            String id,
            String definitionId,
            int definitionVersion,
            String workflowPurposeId,
            String targetModule,
            String targetTypeId,
            String targetId,
            String targetCode,
            String targetLabel,
            String status,
            String currentStepId,
            String startedByActorId,
            String startedByUsername,
            String startedByDisplayName,
            Instant startedAt,
            Instant completedAt,
            Instant cancelledAt,
            String correlationId
    ) { }

    record TimelineEntry(
            String id,
            String instanceId,
            String taskId,
            String actionType,
            String decision,
            String actorId,
            String actorDisplayName,
            String decisionNote,
            String commentText,
            long sequence,
            Instant occurredAt
    ) { }

    record AvailableActionView(
            String transitionId,
            String decision,
            String fromStepId,
            String toStepId,
            boolean reasonRequired,
            boolean commentRequired,
            String requiredPermissionCode,
            String targetModuleCallback,
            boolean permitted
    ) { }
}
