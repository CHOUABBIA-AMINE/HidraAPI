/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.event
 *
 * @Description : Published when a workflow task is created.
 *
 */
package dz.sh.hidra.modules.workflow.domain.event;

import java.time.Instant;

/**
 * Published when a workflow task is created.
 */
public record WorkflowTaskCreatedEvent(
        String eventId,
    String taskId,
    String instanceId,
    Instant occurredAt
) implements WorkflowDomainEvent {

    @Override
    public String eventType() {
        return "WorkflowTaskCreatedEvent";
    }
}
