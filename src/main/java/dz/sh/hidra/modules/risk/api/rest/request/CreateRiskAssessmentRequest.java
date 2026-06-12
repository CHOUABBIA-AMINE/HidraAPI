/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRiskAssessmentRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.request
 *
 * @Description : REST request to create risk assessment.
 *
 */
package dz.sh.hidra.modules.risk.api.rest.request;

import java.time.Instant;

/**
 * REST request to create risk assessment.
 */
public record CreateRiskAssessmentRequest(
        String riskRegisterId,
        String assessmentNumber,
        String title,
        String description,
        String assessmentTypeId,
        String methodologyId,
        String scopeId,
        String riskScenarioId,
        Instant assessmentDate,
        Instant validFrom,
        Instant validTo,
        String assessedByActorId,
        String assessedByDisplayNameSnapshot
) {
}
