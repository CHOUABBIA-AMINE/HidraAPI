/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAcceptedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.event
 *
 * @Description : Published when a risk is accepted.
 *
 */
package dz.sh.hidra.modules.risk.domain.event;

import java.time.Instant;

/**
 * Published when a risk is accepted.
 */
public record RiskAcceptedEvent(
        String eventId,
    String riskAcceptanceId,
    String riskAssessmentId,
    Instant occurredAt
) implements RiskDomainEvent {

    @Override
    public String eventType() {
        return "RiskAcceptedEvent";
    }
}
