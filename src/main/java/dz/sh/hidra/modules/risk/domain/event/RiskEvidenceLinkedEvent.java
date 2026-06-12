/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskEvidenceLinkedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.event
 *
 * @Description : Published when evidence is linked to a risk assessment.
 *
 */
package dz.sh.hidra.modules.risk.domain.event;

import java.time.Instant;

/**
 * Published when evidence is linked to a risk assessment.
 */
public record RiskEvidenceLinkedEvent(
        String eventId,
    String riskAssessmentId,
    String evidenceId,
    Instant occurredAt
) implements RiskDomainEvent {

    @Override
    public String eventType() {
        return "RiskEvidenceLinkedEvent";
    }
}
