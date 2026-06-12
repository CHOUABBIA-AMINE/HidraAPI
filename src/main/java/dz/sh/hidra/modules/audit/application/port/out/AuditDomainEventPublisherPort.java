/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditDomainEventPublisherPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Publisher port for audit domain events.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

import dz.sh.hidra.modules.audit.domain.event.AuditDomainEvent;

/**
 * Publisher port for audit domain events.
 */
public interface AuditDomainEventPublisherPort {

    void publish(AuditDomainEvent event);
}
