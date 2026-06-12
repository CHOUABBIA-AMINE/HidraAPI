/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskTreatmentPlanCreatedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.event
 *
 * @Description : Published when a treatment plan is created.
 *
 */
package dz.sh.hidra.modules.risk.domain.event;

import java.time.Instant;

/**
 * Published when a treatment plan is created.
 */
public record RiskTreatmentPlanCreatedEvent(
        String eventId,
    String riskTreatmentPlanId,
    String riskAssessmentId,
    Instant occurredAt
) implements RiskDomainEvent {

    @Override
    public String eventType() {
        return "RiskTreatmentPlanCreatedEvent";
    }
}
