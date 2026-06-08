/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActionDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.dto
 *
 * @Description : Application DTO for workflow actions.
 *
 */
package dz.sh.hidra.modules.workflow.application.dto;

import java.time.Instant;

/**
 * Application DTO for workflow actions.
 *
 * <p>Architecture role:
 * Application-layer workflow data transfer projection. It is technology-neutral and must not depend
 * on REST, persistence, Spring, JPA, telemetry implementation classes, topology implementation
 * classes, planning, monitoring, incidents, audit implementation, integration, analytics, reporting,
 * or notification.
 */
public record WorkflowActionDto(
        String id,
        String instanceId,
        String taskId,
        String actionType,
        String decision,
        WorkflowReasonReferenceDto reason,
        String decisionNote,
        String comment,
        WorkflowActorReferenceDto actor,
        String organizationUnitId,
        String organizationUnitNameSnapshot,
        String correlationId,
        Instant actedAt) {

    public WorkflowActionDto {
    }
}
