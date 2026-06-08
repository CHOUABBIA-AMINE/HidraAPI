/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.dto
 *
 * @Description : Application DTO for workflow instances.
 *
 */
package dz.sh.hidra.modules.workflow.application.dto;

import java.time.Instant;
import java.util.List;

/**
 * Application DTO for workflow instances.
 *
 * <p>Architecture role:
 * Application-layer workflow data transfer projection. It is technology-neutral and must not depend
 * on REST, persistence, Spring, JPA, telemetry implementation classes, topology implementation
 * classes, planning, monitoring, incidents, audit implementation, integration, analytics, reporting,
 * or notification.
 */
public record WorkflowInstanceDto(
        String id,
        String definitionId,
        int definitionVersion,
        WorkflowTargetReferenceDto target,
        String status,
        String currentStepId,
        WorkflowActorReferenceDto startedBy,
        Instant startedAt,
        Instant completedAt,
        Instant cancelledAt,
        String correlationId,
        List<WorkflowTaskDto> tasks,
        List<WorkflowActionDto> actions,
        Instant createdAt,
        Instant updatedAt) {

    public WorkflowInstanceDto {
    }
}
