/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActionRecordedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.event
 *
 * @Description : Published when a workflow action is recorded.
 *
 */
package dz.sh.hidra.modules.workflow.domain.event;

import java.time.Instant;

/**
 * Published when a workflow action is recorded.
 */
public record WorkflowActionRecordedEvent(
        String eventId,
    String actionId,
    String instanceId,
    Instant occurredAt
) implements WorkflowDomainEvent {

    @Override
    public String eventType() {
        return "WorkflowActionRecordedEvent";
    }
}
