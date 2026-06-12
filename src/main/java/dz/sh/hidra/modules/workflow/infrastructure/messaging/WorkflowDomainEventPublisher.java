/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.messaging
 *
 * @Description : In-memory workflow domain event publisher.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.messaging;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowDomainEventPublisherPort;
import dz.sh.hidra.modules.workflow.domain.event.WorkflowDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory workflow domain event publisher.
 */
public class WorkflowDomainEventPublisher implements WorkflowDomainEventPublisherPort {

    private final List<WorkflowDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(WorkflowDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<WorkflowDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
