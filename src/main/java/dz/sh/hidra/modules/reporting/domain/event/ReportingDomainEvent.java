/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.event
 *
 * @Description : Reporting domain event contract.
 *
 */
package dz.sh.hidra.modules.reporting.domain.event;

import java.time.Instant;

/**
 * Reporting domain event contract.
 */
public interface ReportingDomainEvent {

    String eventId();

    String aggregateId();

    String aggregateType();

    String eventType();

    Instant occurredAt();

    String correlationId();

    String actorId();
}
