/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.event
 *
 * @Description : Leak detection domain event contract.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.event;

import java.time.Instant;

/**
 * Leak detection domain event contract.
 */
public interface LeakDetectionDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
