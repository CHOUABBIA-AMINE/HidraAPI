/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionDomainEventPublisherPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.out
 *
 * @Description : Publisher port for leak detection domain events.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.port.out;

import dz.sh.hidra.modules.leakdetection.domain.event.LeakDetectionDomainEvent;

/**
 * Publisher port for leak detection domain events.
 */
public interface LeakDetectionDomainEventPublisherPort {

    void publish(LeakDetectionDomainEvent event);
}
