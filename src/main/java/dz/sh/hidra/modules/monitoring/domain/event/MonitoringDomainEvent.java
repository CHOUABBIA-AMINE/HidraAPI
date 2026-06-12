/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.event
 *
 * @Description : Monitoring domain event contract.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.event;

import java.time.Instant;

/**
 * Monitoring domain event contract.
 */
public interface MonitoringDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
