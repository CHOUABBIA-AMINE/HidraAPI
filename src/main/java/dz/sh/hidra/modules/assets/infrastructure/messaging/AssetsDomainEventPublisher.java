/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsDomainEventPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.messaging
 *
 * @Description : In-memory assets domain event publisher.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.messaging;

import dz.sh.hidra.modules.assets.application.port.out.AssetsDomainEventPublisherPort;
import dz.sh.hidra.modules.assets.domain.event.AssetsDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory assets domain event publisher.
 */
public class AssetsDomainEventPublisher implements AssetsDomainEventPublisherPort {

    private final List<AssetsDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(AssetsDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<AssetsDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
