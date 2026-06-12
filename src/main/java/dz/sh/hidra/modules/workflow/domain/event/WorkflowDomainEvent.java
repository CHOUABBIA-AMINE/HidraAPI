/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.event
 *
 * @Description : Workflow domain event contract.
 *
 */
package dz.sh.hidra.modules.workflow.domain.event;

import java.time.Instant;

/**
 * Workflow domain event contract.
 */
public interface WorkflowDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
