/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.messaging
 *
 * @Description : In-memory telemetry domain event publisher.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.messaging;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryDomainEventPublisherPort;
import dz.sh.hidra.modules.telemetry.domain.event.TelemetryDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory telemetry domain event publisher.
 */
public class TelemetryDomainEventPublisher implements TelemetryDomainEventPublisherPort {

    private final List<TelemetryDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(TelemetryDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<TelemetryDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
