/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityRevocationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Ends authorized, workflow-approved responsibility assignments without deleting history.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract;
import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract.Event;
import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract.Operation;
import dz.sh.hidra.modules.organization.application.command.ResponsibilityOperationContext;
import dz.sh.hidra.modules.organization.application.command.RevokeResponsibilityCommand;
import dz.sh.hidra.modules.organization.application.port.in.RevokeResponsibilityUseCase;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.workflow.application.contract.organization.OrganizationResponsibilityWorkflowContract;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResponsibilityRevocationApplicationService implements RevokeResponsibilityUseCase {

    public static final String REVOKE_PERMISSION = "organization:responsibility:revoke";
    private static final String EVENT_TYPE = "ORGANIZATION_RESPONSIBILITY_REVOKED";
    private static final String EVENT_CATEGORY = "BUSINESS";

    private final ResponsibilityAssignmentRepositoryPort responsibilityAssignmentRepositoryPort;
    private final OrganizationResponsibilityWorkflowContract workflow;
    private final OrganizationResponsibilityAuditContract audit;

    public ResponsibilityRevocationApplicationService(
            ResponsibilityAssignmentRepositoryPort responsibilityAssignmentRepositoryPort,
            OrganizationResponsibilityWorkflowContract workflow,
            OrganizationResponsibilityAuditContract audit
    ) {
        this.responsibilityAssignmentRepositoryPort = Objects.requireNonNull(
                responsibilityAssignmentRepositoryPort,
                "Responsibility assignment repository port must not be null."
        );
        this.workflow = Objects.requireNonNull(workflow, "Organization workflow contract must not be null.");
        this.audit = Objects.requireNonNull(audit, "Organization audit contract must not be null.");
    }

    @Override
    @Transactional
    public String revokeResponsibility(RevokeResponsibilityCommand command) {
        Objects.requireNonNull(command, "Revoke responsibility command must not be null.");
        ResponsibilityOperationContext context = command.context();
        requirePermission(context, REVOKE_PERMISSION);
        OrganizationResponsibilityWorkflowContract.ApprovalEvidence approval =
                workflow.requireCompletedApproval(
                        context.workflowInstanceId(),
                        context.operationReference()
                );

        ResponsibilityAssignment existing = responsibilityAssignmentRepositoryPort
                .findById(command.assignmentId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown responsibility assignment: " + command.assignmentId()
                ));

        if (existing.status() == AssignmentStatus.ENDED) {
            return existing.id();
        }
        if (existing.status() == AssignmentStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cancelled responsibility assignment cannot be revoked: " + existing.id()
            );
        }

        Instant now = Instant.now();
        Instant effectiveAt = command.effectiveAt() == null ? now : command.effectiveAt();
        if (existing.validFrom() == null || !effectiveAt.isAfter(existing.validFrom())) {
            throw new IllegalArgumentException(
                    "Responsibility revocation time must be after validFrom."
            );
        }
        if (existing.validTo() != null && effectiveAt.isAfter(existing.validTo())) {
            throw new IllegalArgumentException(
                    "Responsibility revocation cannot extend the existing validity period."
            );
        }

        ResponsibilityAssignment ended = new ResponsibilityAssignment(
                existing.id(),
                existing.responsibilityType(),
                existing.assigneeType(),
                existing.assigneeId(),
                existing.scopeId(),
                existing.description(),
                existing.validFrom(),
                effectiveAt,
                AssignmentStatus.ENDED,
                existing.createdAt(),
                now
        );

        ResponsibilityAssignment saved = responsibilityAssignmentRepositoryPort.save(ended);
        audit.append(new Event(
                EVENT_TYPE,
                EVENT_CATEGORY,
                getClass().getSimpleName(),
                "REVOKE_RESPONSIBILITY",
                context.actorId(),
                context.actorUsername(),
                context.actorDisplayName(),
                "RESPONSIBILITY_ASSIGNMENT",
                saved.id(),
                Operation.UPDATE,
                approval.decision(),
                null,
                approval.workflowInstanceId(),
                approval.workflowTaskId(),
                approval.workflowActionId(),
                context.requestId(),
                context.correlationId(),
                now
        ));
        return saved.id();
    }

    private static void requirePermission(ResponsibilityOperationContext context, String permission) {
        if (!context.hasPermission(permission)) {
            throw new SecurityException("Missing required permission: " + permission);
        }
    }
}
