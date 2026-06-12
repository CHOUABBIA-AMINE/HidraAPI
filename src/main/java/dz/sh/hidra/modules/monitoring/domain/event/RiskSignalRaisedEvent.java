/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskSignalRaisedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.event
 *
 * @Description : Published when monitoring raises a risk signal.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.event;

import dz.sh.hidra.modules.monitoring.domain.value.RiskSignalLevel;
import java.time.Instant;

    /**
     * Published when monitoring raises a risk signal.
     */
    public record RiskSignalRaisedEvent(
            String eventId,
        String riskSignalId,
        RiskSignalLevel riskLevel,
        Instant occurredAt
    ) implements MonitoringDomainEvent {

        @Override
        public String eventType() {
            return "RiskSignalRaisedEvent";
        }
    }
