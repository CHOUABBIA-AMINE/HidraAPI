/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OutboxReadinessHealthIndicator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.health
 *
 * @Description : Reports technical readiness of the platform outbox persistence infrastructure.
 *
 */
package dz.sh.hidra.platform.observability.health;

import dz.sh.hidra.platform.events.outbox.OutboxEventRepository;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class OutboxReadinessHealthIndicator implements HealthIndicator {

    private final OutboxEventRepository outboxEventRepository;

    public OutboxReadinessHealthIndicator(OutboxEventRepository outboxEventRepository) {
        this.outboxEventRepository = outboxEventRepository;
    }

    @Override
    public Health health() {
        try {
            long storedEvents = outboxEventRepository.count();
            return Health.up()
                    .withDetail("component", "platform-outbox")
                    .withDetail("storedEvents", storedEvents)
                    .build();
        } catch (Exception exception) {
            return Health.down()
                    .withDetail("component", "platform-outbox")
                    .withDetail("reason", exception.getClass().getSimpleName())
                    .build();
        }
    }
}
