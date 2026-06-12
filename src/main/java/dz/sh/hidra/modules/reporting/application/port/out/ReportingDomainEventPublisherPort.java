/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingDomainEventPublisherPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 *
 * @Description : Publisher port for reporting domain events.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.out;

import dz.sh.hidra.modules.reporting.domain.event.ReportingDomainEvent;

/**
 * Publisher port for reporting domain events.
 */
public interface ReportingDomainEventPublisherPort {

    void publish(ReportingDomainEvent event);
}
